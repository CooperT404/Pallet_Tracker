this file will contain what every method does and how the logic works, starting with the "MyDataBaseHelper"

lines 23 to 38

this is the database creation this is all the parameter that will be used in the app at current.

lines 41 to 54

this is the onCreate method, this will create the previously mentioned database if there is none currently made. it will also log if the method was called in the console.

line 55 to 67

this is a check to see whether or not an ID already exists within the database: the main functionality of this will be discussed in button creation.
we set string to be used for the query, then will do a boolean check to see if that query returns any matching IDs, if true it returns true. or false if there are no matching IDs

lines 70 to 80

this contains two different methods; one for upgrading the database and the other for deleting the database.
neither of these will be in normal functionality and will NOT be attached to any buttons.

lines 82 to 101

this method get all of the unique IDs from the table and returns them in a List<> format.
it does this by querying only unique IDs from the database; then moves to the first row and adds them to the list until there are no more.

lines 102 to 120

similar to the last method this returns a list of IDs by a buttons tag. this is for data linkage between buttons and the database.
it works the same as the previous method except it takes a string value to use for the query.

lines 123 to 134

this method just inserts data into the database, it takes in all parameters and adds them all in one method.
its made this way to make it reusable in button creation.

lines 135 to 169

this updates the given data that is selected via the two integers. these integers and the way that we can get the all of the matching IDs(rowId) and then select the right Row(RowIndex) from the IDs.
it does this by querying data with the rowId and then moving the cursor to the right position via the rowIndex and then updates all the data in that row with given parameters.

lines 172 to 204

this method will get the String from each Row + Column and return the string equivalent of that data. it does this by taking the rowId and rowIndex; then selects the column with the same name as columnName and returns it.


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
MainActivity

lines 24 to 31

these are all private variables that i used throughout multiple methods

lines 34 to 94

this is the onCreate method, it sets-up the database and the main page of the app.
it also sets-up some navigational buttons for the archives and calculator: the calculator has functionality.
then it sets-up the 'Add Pallet' button and 'Delete' button: Delete does not have functionality at this point.

lines 98 to 137

this method is the input dialog that will be shown upon clicking the 'Add Pallet' button.
this will inflate two different .xml files "custom_layout" and "new_pallet_unique_units"
new_pallet... is the first layout to appear and will dictate how many times the other layout will appear.
it will loop the last layout based on the number inputed into the first layout.

lines 139 to 186

this is the method that will intially put data into the database based on the variable 'unique_Units'.
upon clicking "ok" the method will call itself again and adds 1 to a counter till its equal to the variable 'unique_Units'.
then it will show the dialog in which the user can enter specific info.

lines 190 to 246

this method uses the method from "MyDatabaseHelper" lines 82 to 101
what this method does is create a bunch of buttons based on the amount IDs returned.
then it uses the method from "MyDatabaseHelper" lines 102 to 120.
this makes the next group of button but based off of one ID it gets a list of all matching rows with that ID and makes buttons for each.
upon creation of each these button groups they will be given tags that will be used to get data out of the database.

lines 250 to 311

this method  inflates the .xml file "data_get_from_db_custom_layout".
this method also uses specific IDs and RowIDs as inputs.
intially it will get the data from the database that matches the button tags and then adds them to the edit texts fields in the layout.
upon clicking 'ok' the 'updateData' method will be called and the dialogue will close.

Last Updated 9/23/2024 - Cooper Thomas



