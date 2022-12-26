package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.WorkView;
import constants.MessageConst;
import models.Employee;
import services.WorkService;

//出退勤インスタンスに設定されている値のバリデーションを行うクラス
public class WorkValidator {

    //バリデーションを行う
    public static List<String> validate(
            WorkService service, WorkView wv, Employee employee, LocalDate workDate, Boolean WorkDateDuplicateCheckFlag){
        List<String> errors = new ArrayList<String>();

        //出勤日のチェック
        String WorkDateError = validateWorkDate(service, employee, workDate, WorkDateDuplicateCheckFlag);
        if(!WorkDateError.equals("")) {
            errors.add(WorkDateError);
        }

        //出勤時間のチェック
        String attendanceAtError = validateAttendanceAt(wv.getAttendanceAt());
        if(!attendanceAtError.equals("")) {
            errors.add(attendanceAtError);
        }

        //退勤時間のチェック
        String leavingAtError = validateLeavingAt(wv.getLeavingAt());
        if(!leavingAtError.equals("")) {
            errors.add(leavingAtError);
        }

        return errors;
    }

    public static List<String> validate(WorkView wv){
        List<String> errors = new ArrayList<String>();

        //出勤時間のチェック
        String attendanceAtError = validateAttendanceAt(wv.getAttendanceAt());
        if(!attendanceAtError.equals("")) {
            errors.add(attendanceAtError);
        }

        //退勤時間のチェック
        String leavingAtError = validateLeavingAt(wv.getLeavingAt());
        if(!leavingAtError.equals("")) {
            errors.add(leavingAtError);
        }

        return errors;
    }
    /*
    * 出勤日の入力チェックを行い、エラーメッセージを返却
    * @param codeDuplicateCheckFlag 出勤日の重複チェックを実施するかどうか(実施する:true 実施しない:false)
    */
    private static String validateWorkDate(
            WorkService service, Employee employee, LocalDate workDate, Boolean WorkDateDuplicateCheckFlag) {
            if(WorkDateDuplicateCheckFlag) {
           //社員番号の重複チェックを実施
           long worksCount = isDuplicateWork(service, employee, workDate);

           //同一社員番号が既に登録されている場合はエラーメッセージを返却
           if(worksCount > 0) {
               return MessageConst.E_WORK_TIME_EXIST.getMessage();
           }
        }

       //エラーが無い場合は空文字を返却
       return "";
   }

    /*
    * @param service EmployeeServiceのインスタンス
    * @param code 社員番号
    * @return 従業員テーブルに登録されている同一社員番号のデータの件数
    */
   private static long isDuplicateWork(WorkService service, Employee employee, LocalDate workDate) {
       long worksCount = service.countByWorkDate(employee, workDate);
       return worksCount;
   }

     /*
     * 出勤時刻に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateAttendanceAt(String attendanceAt) {
        if(attendanceAt == null || attendanceAt.equals("")) {
            return MessageConst.E_NOATTENDANCE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

     /*
     * 退勤時刻に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content 内容
     * @return エラーメッセージ
     */
    private static String validateLeavingAt(String leavingAt) {
        if(leavingAt == null || leavingAt.equals("")) {
            return MessageConst.E_NOLEAVING.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
}