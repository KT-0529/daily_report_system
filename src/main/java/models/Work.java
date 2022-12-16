package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_EMP)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_WORK_GET_ALL,
            query = JpaConst.Q_WORK_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_WORK_GET_ALL_MINE,
            query = JpaConst.Q_WORK_GET_ALL_MINE_DEF)
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Work {

     /*
     * id
     */
    @Id
    @Column(name = JpaConst.WORK_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     /*
     * 氏名
     */
    @Column(name = JpaConst.WORK_COL_NAME, nullable = false)
    private String name;

     /*
     * 出勤日時
     */
    @Column(name = JpaConst.WORK_COL_ATTENDANCE_AT, nullable = false)
    private LocalDateTime attendanceAt;

     /*
     * 退勤日時
     */
    @Column(name = JpaConst.WORK_COL_LEAVING_AT, nullable = false)
    private LocalDateTime leavingAt;
}