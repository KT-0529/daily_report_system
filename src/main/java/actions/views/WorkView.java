package actions.views;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 従業員情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)

public class WorkView {

    //id
    private Integer id;

    //氏名
    private EmployeeView employee;

    //出勤日
    private LocalDate workDate;

    //出勤日時
    private String attendanceAt;

    //退勤日時
    private String leavingAt;

    //削除フラグ
    private Integer deleteFlag;
}