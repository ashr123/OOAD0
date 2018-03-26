import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Represents an employee in a neighborhood grocery store.
 * Also immutable object.
 */
public class Employee
{
	private static final String DB_CON_URL="jdbc:sqlite:mydb.db";
	private final int ID;
	private String firstName, lastName;
	private Date leavingDate;
	private double salary;
	
	//Initiates the DB for the first time if not exists.
	static
	{
		synchronized (Employee.class)
		{
			try (Connection conn=DriverManager.getConnection(DB_CON_URL);
			     Statement stmt=conn.createStatement())
			{
//				stmt.execute("DROP TABLE IF EXISTS Employees;");
				stmt.execute("CREATE TABLE IF NOT EXISTS Employees"+
				             '('+
				             "ID INTEGER PRIMARY KEY CHECK (ID BETWEEN 100000000 AND 999999999),"+
				             "firstName VARCHAR(20) NOT NULL,"+
				             "lastName VARCHAR(20) NOT NULL,"+
				             "salary REAL NOT NULL CHECK (salary>=0),"+
				             "leavingDate TEXT DEFAULT NULL"+
				             ");"
				            );
			}
			catch (SQLException e)
			{
				System.out.println(e);
				System.exit(1);
			}
		}
	}
	
	/**
	 * <p>Constructor</p>
	 * Private because it's not allowed to create an independent {@link Employee} outside the DB.
	 * @param ID the Identity number of the employee
	 * @param firstName the first name of the employee
	 * @param lastName the last name of the employee
	 * @param leavingDate {@code null} if the employee is working in the store, otherwise, indicates the date of leaving
	 * @param salary the salary of the employee
	 */
	private Employee(int ID, String firstName, String lastName, Date leavingDate, double salary)
	{
		this.ID=ID;
		this.firstName=firstName;
		this.lastName=lastName;
		this.leavingDate=leavingDate;
		this.salary=salary;
	}
	
	/**
	 * Adds a new employee to the store
	 * @param ID the Identity number of the employee
	 * @param firstName the first name of the employee
	 * @param lastName the last name of the employee
	 * @param salary the salary of the employee
	 * @return {@code true} if the employee was added successfully to the DB, {@code false} otherwise
	 */
	public static boolean addEmployee(int ID, String firstName, String lastName, double salary)
	{
		try (Connection conn=DriverManager.getConnection(DB_CON_URL);
		     PreparedStatement stmt=conn.prepareStatement("INSERT INTO Employees (ID, firstName, lastName, salary) VALUES (?, ?, ?, ?);"))
		{
			stmt.setInt(1, ID);
			stmt.setString(2, firstName.trim());
			stmt.setString(3, lastName.trim());
			stmt.setDouble(4, salary);
			stmt.executeUpdate();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Gets an existing employee from the DB by it's ID
	 * @param ID the Identity number of the employee
	 * @return an {@link Employee} if it's ID exists in the DB, {@code null} otherwise
	 */
	public static Employee getEmployee(int ID)
	{
		try (Connection conn=DriverManager.getConnection(DB_CON_URL);
		     PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Employees WHERE ID=?;"))
		{
			stmt.setInt(1, ID);
			try (ResultSet resultSet=stmt.executeQuery())
			{
				if (resultSet.next())
					return new Employee(resultSet.getInt("ID"),
					                    resultSet.getString("firstName"),
					                    resultSet.getString("lastName"),
					                    resultSet.getString("leavingDate")!=null ?
					                    Date.valueOf(resultSet.getString("leavingDate")) :
					                    null,
					                    resultSet.getDouble("salary"));
				else
					return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return null;
		}
	}

	public int getID()
	{
		return ID;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public Date getLeavingDate()
	{
		return leavingDate!=null ? new Date(leavingDate.getTime()) : null;
	}

	public double getSalary()
	{
		return salary;
	}
	
	/**
	 * Updates an existing employee
	 * @param firstName the new first name of the employee
	 * @param lastName the new last name of the employee
	 * @param salary the new salary of the employee
	 * @param isLeaving indicates if the employee is working in the store or not
	 * @return {@code true} if the update was successful, {@code false} otherwise
	 */
	public synchronized boolean updateEmployee(String firstName, String lastName, boolean isLeaving,
	                                           double salary)
	{
		try (Connection conn=DriverManager.getConnection(DB_CON_URL);
		     PreparedStatement stmt=conn.prepareStatement("UPDATE Employees SET firstName=?, lastName=?, salary=?, leavingDate="+(isLeaving ? "date('now')" : "NULL")+" WHERE ID=?;"))
		{
			stmt.setString(1, firstName=firstName.trim());
			stmt.setString(2, lastName=lastName.trim());
			stmt.setDouble(3, salary);
			stmt.setInt(4, getID());
			stmt.executeUpdate();
			this.leavingDate=isLeaving ? Date.valueOf(LocalDate.now()) : null;
			this.firstName=firstName;
			this.lastName=lastName;
			this.salary=salary;
			return true;
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean equals(Object o)
	{
		return (this==o || o!=null) && getClass()==o.getClass() && getID()==((Employee)o).getID();
	}

	@Override
	public String toString()
	{
		return "Employee{"+
		       "ID="+getID()+
		       ", First name='"+getFirstName()+'\''+
		       ", Last name='"+getLastName()+'\''+
		       ", Leaving date="+getLeavingDate()+
		       ", Salary="+getSalary()+
		       '}';
	}
}