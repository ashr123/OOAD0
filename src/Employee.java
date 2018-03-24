import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Represents an employee in a neighborhood grocery store
 */
public class Employee
{
	private final int ID;
	private String firstName, lastName;
	private Date leavingDate;
	private double salary;

	static
	{
		synchronized (Employee.class)
		{
			try (Connection conn=DriverManager.getConnection("jdbc:sqlite:mydb.db");
			     Statement stmt=conn.createStatement())
			{
//				stmt.execute("DROP TABLE Workers;");
				stmt.execute("CREATE TABLE IF NOT EXISTS Workers"+
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
			}
		}
	}

	private Employee(int ID, String firstName, String lastName, Date leavingDate, double salary)
	{
		this.ID=ID;
		this.firstName=firstName;
		this.lastName=lastName;
		this.leavingDate=leavingDate;
		this.salary=salary;
	}

	public static boolean addWorker(int ID, String firstName, String lastName, double salary)
	{
		try (Connection conn=DriverManager.getConnection("jdbc:sqlite:mydb.db");
		     PreparedStatement stmt=conn.prepareStatement(
				     "INSERT INTO Workers (ID, firstName, lastName, salary) VALUES (?, ?, ?, ?);"))
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

	public static Employee getWorker(int ID)
	{
		try (Connection conn=DriverManager.getConnection("jdbc:sqlite:mydb.db");
		     PreparedStatement stmt=conn.prepareStatement("SELECT * FROM Workers WHERE ID=?;"))
		{
			stmt.setInt(1, ID);
			try (ResultSet resultSet=stmt.executeQuery())
			{
				if (resultSet.next())
					return new Employee(resultSet.getInt("ID"),
					                    resultSet.getString("firstName"),
					                    resultSet.getString("lastName"),
					                    Date.valueOf(resultSet.getString("leavingDate")),
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

	public boolean updateWorker(String firstName, String lastName, boolean isLeaving, double salary)
	{
		try (Connection conn=DriverManager.getConnection("jdbc:sqlite:mydb.db");
		     PreparedStatement stmt=conn.prepareStatement(
				     "UPDATE Workers SET firstName=?, lastName=?, salary=?, leavingDate="+(isLeaving ? "date('now')" : "NULL")+" WHERE ID=?;"))
		{
			stmt.setString(1, firstName=firstName.trim());
			stmt.setString(2, lastName=lastName.trim());
			stmt.setDouble(3, salary);
			stmt.setInt(4, ID);
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
		return this==o || o!=null && getClass()==o.getClass() && getID()==((Employee) o).getID();
	}

	@Override
	public String toString()
	{
		return "Employee{"+
		       "ID="+ID+
		       ", First name='"+firstName+'\''+
		       ", Last name='"+lastName+'\''+
		       ", Leaving date="+leavingDate+
		       ", Salary="+salary+
		       '}';
	}
}