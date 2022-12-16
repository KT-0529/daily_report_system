package actions.views;

import java.util.ArrayList;
import java.util.List;

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
    public static Work toModel(WorkView ev) {

        return new Work(
                ev.getId(),
                ev.getName(),
                ev.getAttendanceAt(),
                ev.getLeavingAt());
    }

     /*
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    public static WorkView toView(Work e) {

        if(e == null) {
            return null;
        }

        return new WorkView(
                e.getId(),
                e.getName(),
                e.getAttendanceAt(),
                e.getLeavingAt());
    }

     /*
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<WorkView> toViewList(List<Work> list) {
        List<WorkView> evs = new ArrayList<>();

        for (Work e : list) {
            evs.add(toView(e));
        }

        return evs;
    }

     /*
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Work e, WorkView ev) {
        e.setId(ev.getId());
        e.setName(ev.getName());
        e.setAttendanceAt(ev.getAttendanceAt());
        e.setLeavingAt(ev.getLeavingAt());
    }
}