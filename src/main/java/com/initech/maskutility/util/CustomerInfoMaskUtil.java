package com.initech.maskutility.util;

import com.initech.maskutility.constant.UtilConstant;
import com.initech.maskutility.exception.InvalidInputException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CustomerInfoMaskUtil {

    private CustomerInfoMaskUtil() {
    }

    /*
    INPUT PARAM (String)- Input customer contact info
    OUTPUT PARAM (String)- Returns masked contact info based on the configuration(UtilContant.java) defined for email & phone
    number.
    DESC - This method is used to mask contact info entered by customer contact info can be customer email or phone number.
    Before masking contact info the method does a validation check on the contact info for validity of entered email or
    phone number.Ideally Email or phone number Validity should be performed at the front end as this will avoid
    unnecessary backend service call trip but including in this method does not hurt.
    **Note** -Method throws InvalidInputException - service caller to handle and notify customer
    ASSUMPTIONS EmailID: x@xyz.com, xy@xyz.com are considered valid emailId
    ASSUMPTIONS Phone number : 10 digit standard length USA number and will only contian
    special characters like '(',')','-',' ' Also it will not contain country code.
     */
    public static String maskCustomerContactInfo(String strCustContact)
            throws InvalidInputException {
        if (strCustContact == null || strCustContact.equals(""))
            throw new InvalidInputException(UtilConstant.INVALID_CONTACT_INFO_EXCEPTION_MESSAGE);
        strCustContact = strCustContact.trim();
        //mask email address if customer has entered email in the contact info
        if (isValidMail(strCustContact)) {
            String[] parts = strCustContact.split("@");
            String phoneId = parts[0]; //example@abc.com  -->  phoneId = example

            // no domain or id present throw invalid input exception
            if (parts[1] == null || parts[1].equals("") || parts[0] == null || parts[0].equals(""))
                throw new InvalidInputException(UtilConstant.INVALID_CONTACT_INFO_EXCEPTION_MESSAGE);
            if (parts[0].length() == 1)
                return "*" + "@" + parts[1];  // *@xyz.com is valid email
            if (parts[0].length() == 2)
                return "**" + "@" + parts[1];  // **@xyz.com is valid email
            //mask id part
            String maskedFirstPart = maskInputStr(phoneId, UtilConstant.EMAIL_MASK_POSITION_FROM_START,
                    phoneId.length() - UtilConstant.EMAIL_MASK_POSITION_FROM_ID_END,
                    UtilConstant.MASK_CHARACTER);
            //appending domain part to the masked id part
            return maskedFirstPart + "@" + parts[1];
        }
        //mask phone number if the customer has entered phone number in the contact info
        else if (isValidMobileNumber(strCustContact)) {
            strCustContact = strCustContact.replaceAll("[()\\-\\s]", "").trim();
            return maskInputStr(strCustContact, UtilConstant.PHONE_NUMBER_MASK_START_INDEX,
                    UtilConstant.PHONE_NUMBER_MASK_END_INDEX, UtilConstant.MASK_CHARACTER);
        } else
            throw new InvalidInputException("Invalid Contact Info");
    }

    /*
   INPUT PARAM (String)- Input customer Email
   OUTPUT PARAM (Boolean)- returns True if valid email
    */
    public static boolean isValidMail(String emailStr) {

        String emailStrRegexExpression = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(emailStrRegexExpression).matcher(emailStr).matches();

    }

    /*
   INPUT PARAM (String)- Input customer phone number   OUTPUT PARAM (Boolean)- returns True if valid phone number
   Assumptions Phone number : valid length 10 digit USA phone number
    */
    public static boolean isValidMobileNumber(String phoneNumber) {
        if(phoneNumber.contains("+")) // no country codes allowed
            return false;
        phoneNumber = phoneNumber.replaceAll("[()\\-\\s]", "").trim();
        if (Pattern.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"
                , phoneNumber)) {
            return phoneNumber.length() == 10;
        }
        return false;
    }

    /*
    INPUT PARAM (String)- customer info (Email or phone number)
    OUTPUT PARAM (String)- returns masked customer info
    This method will mask Email or phone number characters from startIndex to endIndex with the maskCharacter as defined
    in the UtilConstant.java
   */
    public static String maskInputStr(String inputStr, int maskStartIndex, int maskEndIndex, char maskChar) {
        int maskLength = maskEndIndex - maskStartIndex;
        if (maskLength <= 0)
            return inputStr;
        String strMaskedPhoneNumber = StringUtils.repeat(maskChar, maskLength);
        return StringUtils.overlay(inputStr, strMaskedPhoneNumber, maskEndIndex,
                maskStartIndex);
    }


}
