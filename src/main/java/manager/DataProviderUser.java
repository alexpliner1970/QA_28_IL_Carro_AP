package manager;

import models.User;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUser {
    @DataProvider
    public Iterator<Object[]> registrationFile() throws IOException {
        List<Object[]> list=new ArrayList<>();
        BufferedReader reader=new BufferedReader(new FileReader(new File("src/test/resources/reg_date.csv")));
        String line=reader.readLine();
        while (line!=null){
            String[]all=line.split(",");
            list.add(new Object[]{new User().setFirsName(all[0]).setLastName(all[1]).setEmail(all[2]).setPassword(all[3])});
            line=reader.readLine();
        }

        return list.iterator();
    }
}
