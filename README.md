# mask-utility 

Problem - statement 
**************************************************************************************************************************************
You are working for a company called "Initech" on a web service with your coworker "Bill". Bill just sent you an email that the customer contact field on the companies website is saving personal customer information in the clear. The contact field on the website has the label "Email or Phone" with a character length of 80 and the data is not validated on the front end. Bill has said the email domain does not have to be masked, but the customer's email must be masked except for the first and last character. Bill said for phone numbers we need to mask the middle three digits and leave everything else in the clear. Bill says he wants a simple utility method he can pass this field too and get a masked result back and store it in the database. Bill also needs to see lots of test proof that this method works. Bill just left for a vacation to Brazil, so document any assumptions that you think Bill would agree with.
****************************************************************************************************************************************

Utility Info -

Mask utility is used to mask customer contact info like email id, phone number with the configurable masking character. Ideally Email or phone number Validity should be performed at the front end as this will avoid
    backend service call trip
    
Masking character configured as = '*'   

Masking length for emailid and phone number are configurable. 


Assumptions EmailID: x@xyz.com, xy@xyz.com are considered valid emailId

Assumptions Phone number : 10 digit standard length USA phone number and can only contian special characters like '(',')','-',' ' Also it will not contain country code.


System requirements : Java Version = 8 and above, Import as maven project
