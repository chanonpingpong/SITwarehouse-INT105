package sitwarehouse;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SITwarehouse {
    
    public static void main(String[] args)  {
        
     /*  GregorianCalendar startDate,endDate;
                startDate = new GregorianCalendar();
                endDate = new GregorianCalendar();
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                
                Date firstDate = startDate.getTime();
                Date lastDate = endDate.getTime();
                String fd = df.format(firstDate);
                String ld = df.format(lastDate);
                
                System.out.println(fd);
                System.out.println(ld);*/
      //  Employee.createEmployee("jame", "jame", "jame", "jame", "jame", "jame", "jame");
        SITOperation.loginMember("jamemez", "kittisak");
    }
    
}
