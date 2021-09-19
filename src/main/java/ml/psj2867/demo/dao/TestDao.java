package ml.psj2867.demo.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

    @Autowired
    private JdbcTemplate template;

    public Object getData1() {
        Object result = 
         template.query("select * from test ", (rs, idx) -> {
            try {
                return getResultMapRows(rs);
            } catch (Exception e) {
                return null;
            }
         }
        );
        return result;
    }
    private List<Map> getResultMapRows(ResultSet rs) throws Exception
    {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map> list = new ArrayList<>();
     
        while(rs.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

}