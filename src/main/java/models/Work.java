package models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//従業員データのDTOモデル
@Table(name = JpaConst.TABLE_WORK)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_WORK_GET_ALL,
            query = JpaConst.Q_WORK_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_WORK_COUNT,
            query = JpaConst.Q_WORK_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_WORK_GET_ALL_MINE,
            query = JpaConst.Q_WORK_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_WORK_COUNT_ALL_MINE,
            query = JpaConst.Q_WORK_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_WORK_COUNT_REGISTERED_BY_EMPLOYEE,
            query = JpaConst.Q_WORK_COUNT_REGISTERED_BY_EMPLOYEE_DEF),
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Work {

    //id
    @Id
    @Column(name = JpaConst.WORK_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //氏名
    @ManyToOne
    @JoinColumn(name = JpaConst.WORK_COL_EMP, nullable = false)
    private Employee employee;

    //出勤日
    @Column(name = JpaConst.WORK_COL_WORK_DATE, nullable = false)
    private LocalDate workDate;

    //出勤日時
    @Column(name = JpaConst.WORK_COL_ATTENDANCE_AT, nullable = false)
    private String attendanceAt;

    //退勤日時
    @Column(name = JpaConst.WORK_COL_LEAVING_AT, nullable = false)
    private String leavingAt;

    //削除フラグ
    @Column(name = JpaConst.WORK_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;
}