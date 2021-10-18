package com.foxrain.sheep.whileblack.util.valid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Created with intellij IDEA. by 2020 04 2020/04/09 12:33 오전 09 User we at 00 33 To change this
 * template use File | Settings | File Templates.
 */
public class RequestResultValidationProcessor {

//  public static ResponseEntity<ApiResponse> returnErrorResponse(Errors errors) {
//
//    // 개별 필드 오류 처리(ex: startDate에 20181322와 같은 값이 들어왔을 때)
//    List<FieldError> fieldErrors = errors.getFieldErrors();
//    if (!fieldErrors.isEmpty()) {
//      String message = fieldErrors.stream()
//          .map(e -> String.format("Property value [%s] of '%s' is invalid.", e.getRejectedValue(), e.getField()))
//          .collect(Collectors.joining(" && "));
//      return ResponseEntity.badRequest().body(new ApiResponse(false, message));
//    }
//
//    // 개별 필드 외의 오류 처리(ex: startDate와 endDate의 차이가 90일을 넘어갈 때)
//    List<ObjectError> objectErrors = errors.getAllErrors();
//    if (!objectErrors.isEmpty()) {
//      String message = objectErrors.stream()
//          .map(e -> String.format("Error in object '%s': %s", e.getObjectName(), e.getDefaultMessage()))
//          .collect(Collectors.joining(" && "));
//      return ResponseEntity.badRequest().body(new ApiResponse(false, message));
//    }
//
//    return ResponseEntity.badRequest().body(new ApiResponse(false, "입력 값을 다시 확인해 주시길 바랍니다."));
//  }

    public static void main(String[] args)
    {
        File file = new File("/Users/we/test_log/issue20210427/scag");
        File file2 = new File("/Users/we/test_log/issue20210427/select.sql");
        int count = 0;
        boolean isBreak = false;


        try (
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(file2));
        )
        {
//            pw.println("SELECT * FROM wmp_ep_product_dev.ws_vendor_category_map WHERE ");
            String line = null;
            while((line = br.readLine()) != null)
            {
                String[] token = line.split("\t", -1);
                pw.print("SELECT * FROM wmp_ep_product.ws_vendor_category_map WHERE ");
                pw.printf(" (vendor_category_1depth = '%s' and " +
                    "vendor_category_2depth = '%s' and " +
                    "vendor_category_3depth = '%s' and " +
                    "vendor_category_4depth = '%s') UNION \n"
                , getX(token[0]), getX(token[1]), getX(token[2]), getX(token[3]));

                count++;
                if (count == 100)
                {
                    isBreak = true;
                }
                if(isBreak)
                {
                    break;
                }
            }
            System.out.println("Done sir");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
    }

    private static String getX(String name)
    {
        return StringUtils.isBlank(name) ? "x" : name;
    }

    public void main1(String[] args)
    {
        IntStream.range(2, 30).noneMatch(s -> s > 1);

        File file = new File("/Users/we/test_log/dockerstudy/verticadata/result2.txt");
        File file2 = new File("/Users/we/test_log/dockerstudy/verticadata/result2_index_100000.txt");

        Pattern pattern = Pattern.compile("\"product_id\":\"(.+?)\"");

        int count = 0;
        boolean isBreak = false;

        try (
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(file2));
            )
        {
            String line = null;
            while((line = br.readLine()) != null)
            {
                count++;
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find())
                {

                    final String group = matcher.group(1);

                    pw.printf("{ \"index\": {\"_id\" : \"%s\"}}\n", group);
                    pw.println(line);
                }
                if (count == 100000)
                {
                    isBreak = true;
                }
                if(isBreak)
                {
                    break;
                }
            }
            System.out.println("Done sir");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
    }
}
