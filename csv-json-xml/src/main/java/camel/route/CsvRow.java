package camel.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@NoArgsConstructor
@AllArgsConstructor
@Data
@CsvRecord(separator = ",", skipFirstLine = true)
public class CsvRow {

    @DataField(pos = 1, name = "id")
    private int id;

    @DataField(pos = 2, name = "name")
    private String name;

    @DataField(pos = 3, name = "age")
    private Integer age;

}
