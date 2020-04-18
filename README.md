# mask-utility 
Mask utility is used to mask customer contact info like email id, phone number with the configurable masking character. Ideally Email or phone number Validity should be performed at the front end as this will avoid
    backend service call trip
    
Masking character configured as = '*'   

Masking length for emailid and phone number are configurable. 


Assumptions EmailID: x@xyz.com, xy@xyz.com are considered valid emailId

Assumptions Phone number : 10 digit standard length USA number and will only contian special characters like '(',')','-',' ' Also it will not contain country code.


System requirements : Java Version = 8 and above, Import as maven project
