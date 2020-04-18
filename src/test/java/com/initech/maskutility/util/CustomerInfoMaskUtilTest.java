package com.initech.maskutility.util;

import com.initech.maskutility.exception.InvalidInputException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExpectedException;

public class CustomerInfoMaskUtilTest {
    private CustomerInfoMaskUtil customerInfoMaskUtil;
    private List<String> valid_customer_info_input_list;
    private List<String> valid_customer_info_expected_output_list;
    private ClassLoader classLoader;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    // Test case setup to read test case inputs from resource files
    @org.junit.Before
    public void setUp() throws Exception {
        String valid_customer_info_input_file = "valid_customer_info_input_file";
        String valid_customer_info_expected_output_file = "valid_customer_info_expected_output_file";
        classLoader = getClass().getClassLoader();
        File input_file = new File(classLoader.getResource(valid_customer_info_input_file).getFile());
        File expected_file = new File(classLoader.getResource(valid_customer_info_expected_output_file).getFile());
        String valid_customer_info_input_file_path = input_file.getAbsolutePath();
        String valid_customer_info_expected_output_file_path = expected_file.getAbsolutePath();
        valid_customer_info_input_list = new ArrayList<String>();
        valid_customer_info_expected_output_list = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    valid_customer_info_input_file_path)));
            String strInput;
            while ((strInput = in.readLine()) != null) {
                valid_customer_info_input_list.add(strInput);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    valid_customer_info_expected_output_file_path)));
            String strInput;
            while ((strInput = in.readLine()) != null) {
                valid_customer_info_expected_output_list.add(strInput);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test case for al valid scenarios of customer contact info
    // abcd@test1.com
    //   emailwithleadingspace@test4.com - email with leading spaces
    //   emailwithleadingntrailingspace@test4.com   - email with leading and trailing spaces
    //   x@xyz.com   - email with single character id length(excluding the domain length) - Assumed to be valid emailid
    //   xy@xyz.com  - email with two character/digit id length(excluding the domain length) - Assumed to be valid emailid
    //1234567899 - valid 10 digit phone nummber
    //123-456-7891 - valid 10 digit phone number with specialcharacter '-'
    //(123)456-7891  valid 10 digit phone number with specialcharacter '(' ')' '-' and ' '
    //123 456 7899 valid 10 digit phone number with specialcharacter ' '
    @Test
    public void valid_customer_info_input_masked_correctly_and_no_exception_to_be_thrown() {
        try {
            for (int listIdx = 0; listIdx < valid_customer_info_input_list.size(); listIdx++) {
                String expectedResult = valid_customer_info_expected_output_list.get(listIdx);
                String actualOutput = customerInfoMaskUtil.maskCustomerContactInfo(
                        valid_customer_info_input_list.get(listIdx));
                Assert.assertEquals(expectedResult, actualOutput);
                System.out.println("INPUT :  " + valid_customer_info_input_list.get(listIdx) +
                        "     MASKED_OUTPUT :  " + actualOutput);
            }
        } catch (InvalidInputException invalidInputExp) {
            System.out.println(invalidInputExp.getMessage());
        }
    }

    // Test case for empty or null value of email Id or phone number
    @Test(expected = InvalidInputException.class)
    public void empty_null_input_contact_info_throw_exception() throws InvalidInputException {
        File file = new File(classLoader.getResource("null_empty_customer_contact_info").getFile());
        try {
            String strInput;
            String empty_null_input_contact_info = "";
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    file.getAbsolutePath())));
            while ((strInput = in.readLine()) != null) {
                empty_null_input_contact_info = strInput;
            }
            in.close();
            customerInfoMaskUtil.maskCustomerContactInfo(empty_null_input_contact_info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //customerInfoMaskUtil.maskCustomerContactInfo(invalid_customer_info_input);
    }

    // Test case for missing domain in the email ID - XYZ@
    @Test(expected = InvalidInputException.class)
    public void invalid_email_domain_contact_info_throw_exception() throws InvalidInputException {
        File file = new File(classLoader.getResource("invalid_email_domain_contact_info").getFile());
        try {
            String strInput;
            String invalid_email_domain_contact_info = "";
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    file.getAbsolutePath())));
            while ((strInput = in.readLine()) != null) {
                invalid_email_domain_contact_info = strInput;
            }
            in.close();
            customerInfoMaskUtil.maskCustomerContactInfo(invalid_email_domain_contact_info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //customerInfoMaskUtil.maskCustomerContactInfo(invalid_customer_info_input);
    }

    //Test case for phone number less than 7 or greater than 13 digits.
    @Test(expected = InvalidInputException.class)
    public void phone_number_less_than_or_greater_than_10_digits_throw_exception() throws InvalidInputException {
        File file = new File(classLoader.getResource
                ("invalid_phone_number_less_than_and_greater_than_10_digits").getFile());
        try {
            String strInput;
            String invalid_phone_number = "";
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    file.getAbsolutePath())));
            while ((strInput = in.readLine()) != null) {
                invalid_phone_number = strInput;
            }
            in.close();
            customerInfoMaskUtil.maskCustomerContactInfo(invalid_phone_number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test case for email id missing id - @xyz.com
    @Test(expected = InvalidInputException.class)
    public void email_contact_info_missing_id_throw_exception() throws InvalidInputException {
        File file = new File(classLoader.getResource
                ("invalid_emailid_missing_id").getFile());
        try {
            String strInput;
            String invalid_emailid = "";
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    file.getAbsolutePath())));
            while ((strInput = in.readLine()) != null) {
                invalid_emailid = strInput;
            }
            in.close();
            customerInfoMaskUtil.maskCustomerContactInfo(invalid_emailid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Test case for phone number with special character
    @Test(expected = InvalidInputException.class)
    public void phone_number_containing_special_character_throw_exception() throws InvalidInputException {
        File file = new File(classLoader.getResource
                ("phone_number_containing_special_character").getFile());
        try {
            String strInput;
            String invalid_phone_number = "";
            BufferedReader in = new BufferedReader(new FileReader(new File(
                    file.getAbsolutePath())));
            while ((strInput = in.readLine()) != null) {
                invalid_phone_number = strInput;
            }
            in.close();
            customerInfoMaskUtil.maskCustomerContactInfo(invalid_phone_number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}