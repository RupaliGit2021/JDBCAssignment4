import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class JdbcCRUD {
    //static Connection connection=null;

    public static void main( String[] args )
    {
        Connection connection=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Scanner scanner=new Scanner(System.in);

        try {
            connection=JdbcUtil.getConnection();
            System.out.println("select user operation number :");
            System.out.println("1:Insert   2:Select/Retrieve  3:Delete   4:Update");
            int opt=scanner.nextInt();
            switch(opt)
            {
                case 1:
                {

                    String insertQuery="insert into student_details(Name,Address,Gender,DOB,DOJ,DOM)values(?,?,?,?,?,?)";


                    System.out.println("Enter Student Name ::");
                    String name=scanner.next();
                    System.out.println("Enter Address ::");
                    String address=scanner.next();
                    System.out.println("Enter Gender ::");
                    String gender=scanner.next();

                    System.out.println("Enter DOB (dd-MM-yyyy)::");
                    String dob = scanner.next();
                    //convert into simple date format
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-YYYY");
                    java.util.Date udate= sdf.parse(dob);//convert into utility date
                    long l=udate.getTime();//get time in long
                    //convert long into sql date
                    java.sql.Date sdate=new java.sql.Date(l);


                    System.out.println("Enter DOJ(MM-dd-yyyy):: ");
                    String doj=scanner.next();
                    SimpleDateFormat sdf1=new SimpleDateFormat("MM-dd-yyyy");
                    Date udate1=sdf.parse(doj);
                    long l1=udate1.getTime();
                    java.sql.Date sdate1=new java.sql.Date(l);

                    System.out.println("Enter DOM (yyyy-MM-dd):: ");
                    String dom=scanner.next();
                    java.sql.Date sdate3 = java.sql.Date.valueOf(dom);


                    pstmt= connection.prepareStatement(insertQuery);
                    if(pstmt!=null)
                    {
                        pstmt.setString(1,name);
                        pstmt.setString(2,address);
                        pstmt.setString(3,gender);
                        pstmt.setDate(4,sdate);
                        pstmt.setDate(5,sdate1);
                        pstmt.setDate(6,sdate3);

                        int noOfRow = pstmt.executeUpdate();
                        if(noOfRow==1)
                        {
                            System.out.println("Successfully "+noOfRow+" row inserted");
                        }else
                        {
                            System.out.println("record not inserted");
                        }
                    }
                    break;
                }
                case 2:
                {
                    String selectQuery="select * from student_details where id=?";
                    pstmt = connection.prepareStatement(selectQuery);
                    if(pstmt!=null)
                    {
                        System.out.println("Enter Id to retrieve data : ");
                        int id=scanner.nextInt();
                        pstmt.setInt(1,id);
                        rs=pstmt.executeQuery();
                        if(rs!=null)
                        {
                            if(rs.next())
                            {
                                int id1=rs.getInt(1);
                                String name=rs.getString(2);
                                String address=rs.getString(3);
                                String gender=rs.getString(4);
                                java.sql.Date sDate1=rs.getDate(5);

                                String sDate11=SimpleDateFormat.getInstance().format(sDate1);

                                java.sql.Date sDate2=rs.getDate(6);
                                String sDate22=SimpleDateFormat.getInstance().format(sDate2);
                                java.sql.Date sDate3=rs.getDate(7);
                                String sDate33=SimpleDateFormat.getInstance().format(sDate3);

                                System.out.println(id1+" "+name+" "+address+" "+gender+" "+sDate11+" "+sDate22+" "+sDate33);;

                            }
                        }
                    }
                    break;
                }
                case 3:
                {
                    String deleteQuery="delete from student_details where id=? ";
                    pstmt=connection.prepareStatement(deleteQuery);
                    if(pstmt!=null)
                    {
                        System.out.println("Enter student id which you want to delete :");
                        int sId=scanner.nextInt();
                        pstmt.setInt(1,sId);
                        int i=pstmt.executeUpdate();
                        if(i!=0)
                        {
                            System.out.println("Row deleted");
                        }
                        else
                        {
                            System.out.println("record not found");
                        }
                    }
                    break;
                }
                case 4:
                {
                    String updateQuery="update student_details set Address=? where id=?";
                    pstmt=connection.prepareStatement(updateQuery);
                    if(pstmt!=null)
                    {
                        System.out.println("Enter id which you want to update the data :");
                        int id=scanner.nextInt();
                        System.out.println("Enter new address :");
                        String addr=scanner.next();
                        pstmt.setString(1,addr);
                        pstmt.setInt(2,id);
                        int count=pstmt.executeUpdate();

                        if(count!=0)
                        {
                            System.out.println("record updated");
                        }
                        else
                        {
                            System.out.println("record not found");
                        }
                    }
                    break;
                }
                default :
                {
                    System.out.println("Not selected any operatrion");
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            JdbcUtil.closeConnection(pstmt, rs, connection);
            scanner.close();
        }



    }

}
