package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Work;

/*
 * 従業員データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class WorkConverter {

     /*
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev EmployeeViewのインスタンス
     * @return Employeeのインスタンス
     */
    public static Work toModel(WorkView wv) {
        return new Work(
                wv.getId(),
                EmployeeConverter.toModel(wv.getEmployee()),
                wv.getWorkDate(),
                wv.getAttendanceAt(),
                wv.getLeavingAt(),
                wv.getDeleteFlag() == null
                        ? null
                        : wv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                ? JpaConst.WORK_DEL_TRUE
                                : JpaConst.WORK_DEL_FALSE);
    }

     /*
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    public static WorkView toView(Work w) {

        if(w == null) {
            return null;
        }

        return new WorkView(
                w.getId(),
                EmployeeConverter.toView(w.getEmployee()),
                w.getWorkDate(),
                w.getAttendanceAt(),
                w.getLeavingAt(),
                w.getDeleteFlag() == null
                ? null
                : w.getDeleteFlag() == JpaConst.WORK_DEL_TRUE
                        ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

     /*
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<WorkView> toViewList(List<Work> list) {
        List<WorkView> wvs = new ArrayList<>();

        for (Work w : list) {
            wvs.add(toView(w));
        }

        return wvs;
    }

     /*
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Work w, WorkView wv) {
        w.setId(wv.getId());
        w.setEmployee(EmployeeConverter.toModel(wv.getEmployee()));
        w.setWorkDate(wv.getWorkDate());
        w.setAttendanceAt(wv.getAttendanceAt());
        w.setLeavingAt(wv.getLeavingAt());
        w.setDeleteFlag(wv.getDeleteFlag());

    }
}