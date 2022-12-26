package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.WorkView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.WorkService;

 /*
 * 出退勤に関する処理を行うActionクラス
 */
public class WorkAction extends ActionBase {

    private WorkService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new WorkService();

        //メソッドを実行
        invoke();
        service.close();
    }

     /*
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        List<WorkView> works = service.getMinePerPage(loginEmployee, page);

        //全出退勤記録の件数を取得
        long worksCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.WORKS, works); //取得した出退勤記録
        putRequestScope(AttributeConst.WORK_COUNT, worksCount); //全ての出退勤記録
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_WORK_INDEX);
    }

     /*
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //勤怠情報の空インスタンスに、勤怠の日付＝今日の日付を設定する
        WorkView wv = new WorkView();
        wv.setWorkDate(LocalDate.now());
        putRequestScope(AttributeConst.WORK, wv); //日付のみ設定済みの勤怠インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_WORK_NEW);

    }

     /*
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if(getRequestParam(AttributeConst.WORK_DATE) == null
                    || getRequestParam(AttributeConst.WORK_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.WORK_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとに日報情報のインスタンスを作成する
            WorkView wv = new WorkView(
                    null,
                    ev,
                    day,
                    getRequestParam(AttributeConst.WORK_ATTENDANCE),
                    getRequestParam(AttributeConst.WORK_LEAVING),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            List<String> errors = service.create(wv, EmployeeConverter.toModel(ev), day);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.WORK, wv);//入力された情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_WORK_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_WORK, ForwardConst.CMD_INDEX);
            }
        }
    }

     /*
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件にデータを取得する
        WorkView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WORK_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (wv == null || ev.getId() != wv.getEmployee().getId()) {
            //該当のデータが存在しない、または
            //ログインしている従業員が日報の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.WORK, wv); //取得した日報データ

            //編集画面を表示
            forward(ForwardConst.FW_WORK_EDIT);
        }

    }

     /*
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件にデータを取得する
            WorkView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WORK_ID)));

            //入力された内容を設定する
            wv.setAttendanceAt(getRequestParam(AttributeConst.WORK_ATTENDANCE));
            wv.setLeavingAt(getRequestParam(AttributeConst.WORK_LEAVING));

            List<String> errors = service.update(wv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.WORK, wv); //入力された情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_WORK_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_WORK, ForwardConst.CMD_INDEX);

            }
        }
    }
}