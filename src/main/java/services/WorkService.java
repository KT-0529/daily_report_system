package services;

import java.time.LocalDate;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.WorkConverter;
import actions.views.WorkView;
import constants.JpaConst;
import models.Employee;
import models.Work;
import models.validators.WorkValidator;

//日報テーブルの操作に関わる処理を行うクラス
public class WorkService extends ServiceBase {

     /*
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しWorkViewのリストで返却する
     */
    public List<WorkView> getMinePerPage(EmployeeView employee, int page) {

        List<Work> works = em.createNamedQuery(JpaConst.Q_WORK_GET_ALL_MINE, Work.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return WorkConverter.toViewList(works);
    }

     /*
     * 指定した従業員が作成した出退勤記録の件数を取得し、返却する
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_WORK_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

     /*
     * 指定されたページ数の一覧画面に表示する日報データを取得し、WorkViewのリストで返却する
     */
    public List<WorkView> getAllPerPage(int page) {

        List<Work> works = em.createNamedQuery(JpaConst.Q_WORK_GET_ALL, Work.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return WorkConverter.toViewList(works);
    }

     /*
     * 出退勤テーブルのデータの件数を取得し、返却する
     */
    public long countAll() {
        long works_count = (long) em.createNamedQuery(JpaConst.Q_WORK_COUNT, Long.class)
                .getSingleResult();
        return works_count;
    }

     /*
     * idを条件に取得したデータをWorkViewのインスタンスで返却する
     */
    public WorkView findOne(int id) {
        return WorkConverter.toView(findOneInternal(id));
    }

     /*
     * 画面から入力された登録内容を元にデータを1件作成し、出退勤一覧に登録する
     */
    public List<String> create(WorkView wv, Employee employee, LocalDate workDate) {
        List<String> errors = WorkValidator.validate(this, wv, employee, workDate, true);
        if (errors.size() == 0) {
            createInternal(wv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

     /*
     * 画面から入力された日報の登録内容を元に、出退勤を更新する
     */
    public List<String> update(WorkView wv) {

        //バリデーションを行う
        List<String> errors = WorkValidator.validate(wv);

        if (errors.size() == 0) {
            updateInternal(wv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

     /*
     * idを条件にデータを1件取得する
     */
    private Work findOneInternal(int id) {
        return em.find(Work.class, id);
    }

     /*
     * 出退勤を1件登録する
     */
    private void createInternal(WorkView wv) {

        em.getTransaction().begin();
        em.persist(WorkConverter.toModel(wv));
        em.getTransaction().commit();

    }

     /*
     * 日報データを更新する
     * @param rv 日報データ
     */
    private void updateInternal(WorkView wv) {

        em.getTransaction().begin();
        Work w = findOneInternal(wv.getId());
        WorkConverter.copyViewToModel(w, wv);
        em.getTransaction().commit();

    }

   /*
   * 社員番号を条件に該当するデータの件数を取得し、返却する
   * @param code 社員番号
   * @return 該当するデータの件数
   */
  public long countByWorkDate(Employee employee, LocalDate workDate) {
      //指定した社員番号を保持する従業員の件数を取得する
      long works_count = (long)em.createNamedQuery(JpaConst.Q_WORK_COUNT_REGISTERED_BY_EMPLOYEE, Long.class)
              .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, employee)
              .setParameter(JpaConst.JPQL_PARM_WORKDATE, workDate)
              .getSingleResult();
      return works_count;
  }
}