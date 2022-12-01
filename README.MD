## Execution
Should be executed under JVM of version 17 or higher
### Example
`java -jar build.jar rules.txt products.txt 12000`
##### Where:
- `build.jar` - name of executable file
- `rules.txt` - name of text file with `\n` separated rules
- `products.txt` - name of text file containing `\n` separated sample products
- `12000` - minimal satisfying product score
## Assumptions:
 - salesman product list is accessible via some public API
 - salesman API output is small enough to be processed in one go (no pagination required)
 - condition values are tested by exact matching
 - cost has `Double` format both in rules and products
 - supported condition operators are: `<`, `>`, `==,` `BETWEEN x AND y`, `IN (x, y, ...)`
 - `<`, `>`, `BETWEEN x AND y` compare only numbers
 - `==,` `IN (x, y, ...)` compares numbers, booleans and strings
 - conditions are combined only by `&&` (`||` is not supported)
 - products always have `cost` attribute
 - absent attribute value implies unmatched condition

## Sample data format
#### Rule
`attributeName1 < val1 && attributeName2 IN (val2, val3, val4) -> scoreVal`
#### Product
`attributeName1:val1;attributeName2:val2`
