package DataBase;

import java.sql.*;
import java.util.Scanner;

public class StudentQuery {

    public static void outputQuery(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("select * from students");
        System.out.println("=================================");
        while (res.next()) {
            System.out.println("ID студента : " + res.getInt("student_id"));
            System.out.println("Имя студента : " + res.getString("fullname"));
            System.out.println("=================================");
        }
        statement.close();
        res.close();
    }

    public static void deleteByValuesQuery(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from students where students.fullname=(?) and students.group=(?) and students.admission_year=(?)");
        boolean notFinished = true;
        while (notFinished) {
            System.out.println("Введите полное имя студента : ");
            preparedStatement.setString(1, new Scanner(System.in).nextLine());
            System.out.println("Введите группу студента : ");
            preparedStatement.setString(2, new Scanner(System.in).nextLine());
            System.out.println("Введите год поступления студента : ");
            preparedStatement.setInt(3, new Scanner(System.in).nextInt());
            preparedStatement.executeUpdate();
            System.out.println("Желаете ли вы закончить удаление студентов ? да,нет");
            switch (new Scanner(System.in).nextLine()) {
                case "да":
                    notFinished = false;
                    break;
                case "нет":
                    notFinished = true;
                    break;
            }
        }
        preparedStatement.close();
    }

    public static void deleteByIDQuery(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from students where students.student_id=(?)");
        boolean notFinished = true;
        while (notFinished) {
            System.out.println("Введите ID студента : ");
            preparedStatement.setInt(1, new Scanner(System.in).nextInt());
            preparedStatement.executeUpdate();
            System.out.println("Желаете ли вы закончить удаление студентов ? да,нет");
            switch (new Scanner(System.in).nextLine()) {
                case "да":
                    notFinished = false;
                    break;
                case "нет":
                    notFinished = true;
                    break;
            }
        }
        preparedStatement.close();
    }

    public static void addQuery(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into students (fullname,`group`,admission_year) values(?,?,?)");
        boolean notFinished = true;
        while (notFinished) {
            System.out.println("Введите полное имя студента : ");
            preparedStatement.setString(1, new Scanner(System.in).nextLine());
            System.out.println("Введите группу студента : ");
            preparedStatement.setString(2, new Scanner(System.in).nextLine());
            System.out.println("Введите год поступления студента : ");
            preparedStatement.setInt(3, new Scanner(System.in).nextInt());
            preparedStatement.executeUpdate();
            System.out.println("Желаете ли вы закончить добавление студентов ? да,нет");
            switch (new Scanner(System.in).nextLine()) {
                case "да":
                    notFinished = false;
                    break;
                case "нет":
                    notFinished = true;
                    break;
            }
        }
        preparedStatement.close();
    }

    public static void condition(Connection connection) throws SQLException {
        do {
            System.out.println("Какое действие вы хотите совершить в база данных ? 1-удалить студента, 2-добавить студента, 3-вывести информацию о всех студентах");
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    System.out.println("Каким именно способом вы хотите удалить студента ? 1-по индексу, 2-по значению");
                    switch (new Scanner(System.in).nextLine().toLowerCase()) {
                        case "по индексу":
                            deleteByIDQuery(connection);
                            break;
                        case "по значению":
                            deleteByValuesQuery(connection);
                            break;
                        default:
                            System.out.println("Вы ввели неправильное значение...");
                            condition(connection);
                    }
                    break;
                case 2:
                    addQuery(connection);
                    break;
                case 3:
                    outputQuery(connection);
                    break;
                default:
                    System.out.println("Вы ввели неправильное число...");
                    condition(connection);
            }
            System.out.println("Хотите ли вы совершить еще какие-либо действия ?: да,нет");
        }while (new Scanner(System.in).nextLine().toLowerCase().equals("да"));
        connection.close();
    }
}
