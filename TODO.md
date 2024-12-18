LAYOUT

Wendel
[] - Start Page
[] - Lost Item Page
[] - Found Item Page
vesfefqwefw
Viado
[] - Admin Page
[] - Verification Page


Suggestions on Add
- Create a Lost Item Page & Found Item Page
- attach the listeners after clicking the ok button (then we will now check each type if it is valid)


CHECKS:
check for no image then update, still no image
check for no image then update with new image
include the name of the reporter in the table/search

TODO DAY 1
Fix The layout of admin page
ID | Photo | Type | Subtype | Description Details | location Details | Reporter Name | Status ?

Add Match Table
ID | Lost Item ID | Found item ID | Id Photo | Profile | Status 
View - Just Open the MatchItemFormView.java
Update - Open Verification Page

TODO DAY 2
Finish Verification Page
JSplitPane
 - JTabbedPane 1
    * Lost Item Form (ItemFormView.java)
    * Found Item Form (ItemFormView.java)
 - Verification Form (NEW)
    * Verification ID Photo Upload
    * On-site Pciture (Profile) Upload

- Approve Button [Will go from MATCHED TO RESOLVED] [Will move the state of the items to RETURNED] (Validate if it has id photo and profile photo) [JOPTION PANE TOO TO MAKE SURE]
- Unmatch Button set the lost item and found item of this match to PENDING [Will be deleted from the table] [JOPTION PANE TOO TO MAKE SURE]
-Cancel Button [Will just go to the admin page with the tabbed pane set to the Match Table]

ADDITIONAL:
Add Potential Match table
Lost Item ID | Found Item ID | Lost Item Description | Found Item Description | Lost Item Photo? | Found Item Photo? (I think di na need photo)

