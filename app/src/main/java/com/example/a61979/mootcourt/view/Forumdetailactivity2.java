package com.example.a61979.mootcourt.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a61979.mootcourt.R;
import com.example.a61979.mootcourt.adapter.CommentExpandAdapter;
import com.example.a61979.mootcourt.domain.BComment;
import com.example.a61979.mootcourt.domain.BPost;
import com.example.a61979.mootcourt.domain.BUser;
import com.example.a61979.mootcourt.domain.forumbean.CommentBean;
import com.example.a61979.mootcourt.domain.forumbean.CommentDetailBean;
import com.example.a61979.mootcourt.domain.forumbean.ReplyDetailBean;
import com.example.a61979.mootcourt.domain.forumbean.forumBean;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class Forumdetailactivity2 extends AppCompatActivity {

    private static final String TAG = "ForumDetailActivity";
    private Toolbar toolbar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private  List<ReplyDetailBean> replylist;
    private BottomSheetDialog dialog;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";
    private CircleImageView detailuserLogo;
    private TextView detailuserName;
    private TextView detailtitle;
    private TextView detailstory;
    private forumBean forumdata;
    private BUser MyUser;
    private BPost MyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forumdetailactivity_main);
        MyUser=BmobUser.getCurrentUser(BUser.class);
        initView();
    }

    private void initView() {
        commentsList=new ArrayList<CommentDetailBean>();
        detailuserLogo = (CircleImageView)findViewById(R.id.detail_page_userLogo);
        detailuserName = (TextView)findViewById(R.id.detail_page_userName);
        detailtitle = (TextView)findViewById(R.id.detail_page_title);
        detailstory = (TextView)findViewById(R.id.detail_page_story);
        initdetailpager();
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        expandableListView = (CommentExpandableListView) this.findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) this.findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置左侧箭头
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) this.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("详情");
        BmobQuery<BComment> bmobQuery = new BmobQuery<>();
        bmobQuery.include("author,post");
        bmobQuery.addWhereEqualTo("post",forumdata.getObjectId());
        bmobQuery.findObjects(new FindListener<BComment>() {
            @Override
            public void done(List<BComment> object, BmobException e) {
                if (e==null){
                  //  MyPost=object.get(0)
                    for (int i = 0; i < object.size(); i++) {
                        CommentDetailBean m= new CommentDetailBean(object.get(i).getAuthor().getUsername(),object.get(i).getContent(),
                                object.get(i).getCreatedAt());
                        m.setId(i);
                       // m.setReplyList();
                        commentsList.add(m);

                    }
                    initExpandableListView(commentsList);
                    Toast.makeText(Forumdetailactivity2.this,"Comment详情查询成功："+ object.size(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Forumdetailactivity2.this,"Comment详情查询失败："+ e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
//        BmobQuery<BComment> bmobQuery2 = new BmobQuery<>();
//        bmobQuery2.include("author,comment");
       // bmobQuery2.addWhereEqualTo("comment",forumdata.getObjectId());
        //commentsList = generateTestData();

    }

    private void initdetailpager() {
        Intent intent = this.getIntent();
        forumdata = (forumBean) intent.getSerializableExtra("forum");
        detailuserLogo.setImageResource(forumdata.getImageID());
        detailuserName.setText(forumdata.getUsername());
        detailtitle.setText(forumdata.getTitle());
        detailstory.setText(forumdata.getContent());

    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        //隐藏分组项前面的小箭头
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(Forumdetailactivity2.this, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
                //                if(isExpanded){
                //                    expandableListView.collapseGroup(groupPosition);
                //                }else {
                //                    expandableListView.expandGroup(groupPosition, true);
                //                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(Forumdetailactivity2.this,"点击了回复",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
    //    public void onClick(View view) {
    //        if(view.getId() == R.id.detail_page_do_comment){
    //
    //            showCommentDialog();
    //        }
    //    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(Forumdetailactivity2.this);
        View commentView = LayoutInflater.from(Forumdetailactivity2.this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        /**
         * 问题，bottomsheetdialog默认显示高度256dp,不完全显示，如果想要完全显示，处理的办法
         * 1.通过bottomsheetdialog中contentview得到parentView，通过parentview得到bottomsheetbehavior
         * 2.测量bottomsheetdialog布局中的content的高度，设置peekhight
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    BPost bPost = new BPost();
                    bPost.setObjectId(forumdata.getObjectId());
                    BComment bComment = new BComment();
                    bComment.setContent(commentContent);
                    bComment.setAuthor(MyUser);
                    bComment.setPost(bPost);
                    bComment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            CommentDetailBean detailBean = new CommentDetailBean(MyUser.getUsername(), commentContent,"刚刚");
                            adapter.addTheCommentData(detailBean);
                            Toast.makeText(Forumdetailactivity2.this,"评论成功",Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {
                    Toast.makeText(Forumdetailactivity2.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(Forumdetailactivity2.this);
        View commentView = LayoutInflater.from(Forumdetailactivity2.this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();//去除空白
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
                    Toast.makeText(Forumdetailactivity2.this,"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Forumdetailactivity2.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));//超过两个字时，button变橙色
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }
}
