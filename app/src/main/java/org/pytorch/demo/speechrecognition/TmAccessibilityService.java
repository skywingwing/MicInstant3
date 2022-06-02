package org.pytorch.demo.speechrecognition;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.media.AudioManager;

import java.util.List;

public class TmAccessibilityService extends AccessibilityService {

    public static TmAccessibilityService mService;
    //public Context context=getApplicationContext();



    private final String TAG = getClass().getSimpleName();
    private final String packageName="com.tencent.wemeet.app:id/";

    //flag used to recognize the view
    public final String ViewFlag_FastMeeting="使用个人会议号";
    public static final String vid_Main_JoinMeeting="h0";
    public static final String vid_Main_FastMeeting="gy";
    public static final String vid_FastMeeting_Video="i9";
    public static final String vid_FastMeeting_Beauty="i8";
    public static final String vid_FastMeeting_BeautySet="jb";
    public static final String vid_FastMeeting_PersonalMeetingNum="i_";
    public static final String vid_FastMeeting_StartMeeting="fw";//"a4b";//"fw";
    public static final String vid_JoinMeeting_cb="ay5";
    public static final String vid_JoinMeeting_vgBeauty="a2j";
    public static final String vid_JoinMeeting_vgMicon="b01";

    public static final String vid_InMeeting_Mic="a12";//"fe";//"baf";//"b67";

    private static final int vMAIN=0;
    private static final int vFASTMEETING=1;
    private static final int vJOINMEETING=2;
    private static final int vINMEETING=3;
    private static final int vOTHERS=4;

    private AudioManager mAudioManager;

    private int nowView=vOTHERS;
    private boolean find=false;

    AccessibilityNodeInfo nodeInfo;
    Thread FloatingWindow_thread;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "ACC::onAccessibilityEvent: " + event.getEventType());

        //TYPE_WINDOW_STATE_CHANGED == 32
        if (AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED == event
                .getEventType()) {
            nodeInfo = getRootInActiveWindow();
            //event.getSource();
//            List<AccessibilityWindowInfo>windowlist=getWindows();
//            for (AccessibilityWindowInfo window:windowlist){
//                AccessibilityNodeInfo node=window.getRoot();
//
//            }
            Log.i(TAG, "ACC::onAccessibilityEvent: TYPE_WINDOW_CONTENT_CHANGED nodeInfo=" + nodeInfo);
            if (nodeInfo == null) {
                return;
            }
            if (!nodeInfo.findAccessibilityNodeInfosByViewId(packageName+vid_FastMeeting_Video).isEmpty()){
                Log.i(TAG, "ACC::onAccessibilityEvent: Show mFastMeeting...");
                nowView=vFASTMEETING;
                find=true;
                FloatWindow.mService.showFloatWindow(FloatWindow.mService.mFastMeeting,0);
                //ClickViewbyText("点击设置");
                //ClickView("fw");
                //ClickView("jb");
            }
            else if (!nodeInfo.findAccessibilityNodeInfosByViewId(packageName+vid_JoinMeeting_cb).isEmpty()){
                Log.i(TAG, "ACC::onAccessibilityEvent: Show mJoinMeeting...");
                //getWinToken();
                FloatWindow.mService.showFloatWindow(FloatWindow.mService.mJoinMeeting,getViewBound(vid_JoinMeeting_vgMicon).top-200);
                nowView=vJOINMEETING;
                //FloatWindow.mService.showFloatWindow(FloatWindow.mService.mFastMeeting);
            }
            else if (!nodeInfo.findAccessibilityNodeInfosByViewId(packageName+vid_Main_JoinMeeting).isEmpty()){
                Log.i(TAG, "ACC::onAccessibilityEvent: Main");
                FloatWindow.mService.removeFloatWindow();
                nowView=vMAIN;
            }
            else if (!nodeInfo.findAccessibilityNodeInfosByViewId(packageName+vid_InMeeting_Mic).isEmpty()){
                Log.i(TAG, "ACC::onAccessibilityEvent: InMeeting");
                FloatWindow.mService.removeFloatWindow();
                nowView=vINMEETING;
            }
            else {
                Log.i(TAG, "ACC::onAccessibilityEvent: Unknown View");
                nowView=vOTHERS;
                FloatWindow.mService.removeFloatWindow();
            }

//            List<AccessibilityNodeInfo> list = nodeInfo
//                    .findAccessibilityNodeInfosByViewId(packageName+"i9");
//            for (AccessibilityNodeInfo node : list) {
//                Log.i(TAG, "ACC::onAccessibilityEvent: left_button " + node);
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }
//
//            list = nodeInfo
//                    .findAccessibilityNodeInfosByViewId("android:id/button1");
//            for (AccessibilityNodeInfo node : list) {
//                Log.i(TAG, "ACC::onAccessibilityEvent: button1 " + node);
//                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }
        }

        else if(AccessibilityEvent.TYPE_VIEW_CLICKED == event
                .getEventType()){
            nodeInfo = event.getSource();

//            Log.i(TAG, "ACC::onAccessibilityEvent: TYPE_VIEW_CLICKED nodeInfo=" + nodeInfo);
            if (nodeInfo == null) {
                Log.i(TAG,"Warning: NodeInfo Null.");
                return;
            }
            Log.i(TAG,"ViewID:"+nodeInfo.getViewIdResourceName());
//            List<AccessibilityNodeInfo> list = nodeInfo
//                    .findAccessibilityNodeInfosByViewId(packageName+vid_Main_FastMeeting);
//            if(!list.isEmpty()){
//                Log.i(TAG, "ACC::onAccessibilityEvent: Show mFastMeeting...");
//                FloatWindow.mService.showFloatWindow(FloatWindow.mService.mFastMeeting);
//            }
//            else {
//                list = nodeInfo
//                        .findAccessibilityNodeInfosByViewId(packageName+vid_Main_JoinMeeting);
//                if(!list.isEmpty()){
//                    Log.i(TAG, "ACC::onAccessibilityEvent: Show mJoinMeeting...");
//                    FloatWindow.mService.showFloatWindow(FloatWindow.mService.mFastMeeting);
//                }
//            }
        }

    }

    public void ClickViewbyText(String text){
        List<AccessibilityNodeInfo> list = nodeInfo
                .findAccessibilityNodeInfosByText(text);
        for (AccessibilityNodeInfo node : list) {
            Log.i(TAG, "ACC::onAccessibilityEvent: left_button " + node);
            //node.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    public void ClickView(String ViewID){
        if (nodeInfo!=null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId(packageName + ViewID);
            if(list.isEmpty()){
                Log.i(TAG,"Warning: Failed to find the view!");
            }
            else {
                for (AccessibilityNodeInfo node : list) {
                    Log.i(TAG, "ACC::onAccessibilityEvent: Click View " + node);
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    //System.out.println("info:"+node.getStateDescription());
                }
            }
        }
        else {
            Log.i(TAG,"Warning: Try to perform click on null nodeInfo!");
        }
    }

    public String GetSwitchState(String ViewID){
        if (nodeInfo!=null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId(packageName + ViewID);
            if(list.isEmpty()){
                Log.i(TAG,"Warning: Failed to find the view!");
            }
            else {
                for (AccessibilityNodeInfo node : list) {
                    Log.i(TAG, "ACC::onAccessibilityEvent: Click View " + node);
                    return node.getStateDescription().toString();
                    //System.out.println("info:"+node.getStateDescription());
                }
            }
        }
        else {
            Log.i(TAG,"Warning: Try to perform click on null nodeInfo!");
        }
        return null;
    }

    public int CheckMicOn(){
        if (nodeInfo!=null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("解除静音");
            if(!list.isEmpty()){
                return 0;
            }
            list = nodeInfo
                    .findAccessibilityNodeInfosByText("静音");
            if(!list.isEmpty()){
                return 1;
            }
            Log.i(TAG,"CheckMicOn: Warning: Mic State Wrong!");
        }
        else {
            Log.i(TAG,"CheckMicOn: Warning: Try to refer null nodeInfo!");
        }
        return -1;

    }



    public void ClickViewbyPosition(String ViewID){
        if (nodeInfo!=null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId(packageName + ViewID);
            if(list.isEmpty()){
                Log.i(TAG,"Warning: Failed to find the view!");
            }
            else {
                for (AccessibilityNodeInfo node : list) {
                    Log.i(TAG, "ACC::onAccessibilityEvent: left_button " + node);
                    int x=getViewBound(ViewID).top+3;
                    int y=getViewBound(ViewID).left+3;
                    //click(x,y);
                    //node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
        else {
            Log.i(TAG,"Warning: Try to perform click on null nodeInfo!");
        }
    }


    public Rect getViewBound(String ViewID){
        Rect rect=new Rect();
        if (nodeInfo!=null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByViewId(packageName + ViewID);
            if(list.isEmpty()){
                Log.i(TAG,"Warning: Failed to find the view!");
            }
            else {
                for (AccessibilityNodeInfo node : list) {
                    Log.i(TAG, "ACC::onAccessibilityEvent: left_button " + node);
                    node.getBoundsInScreen(rect);
                }
            }
        }
        else {
            Log.i(TAG,"Warning: Try to perform click on null nodeInfo!");
        }
        return rect;
    }

//    private void getWinToken(){
//        IBinder Token;
//        if (nodeInfo!=null) {
//            List<AccessibilityNodeInfo> list = nodeInfo
//                    .findAccessibilityNodeInfosByViewId(packageName + "jw");
//            if(list.isEmpty()){
//                Log.i(TAG,"Warning: Failed to find the view!");
//            }
//            else {
//                for (AccessibilityNodeInfo node : list) {
//                    Log.i(TAG, "ACC::onAccessibilityEvent: ViewID " + node.getViewIdResourceName());
//                    ArrayList<View>Viewlist = new ArrayList<View>();
//                    findViewsWithText(Viewlist,nodeInfo.getContentDescription(), FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
//
//
//                }
//            }
//        }
//        else {
//            Log.i(TAG,"Warning: Try to perform click on null nodeInfo!");
//        }
//    }

    public void SetMicMute(boolean state){

        mAudioManager.setMicrophoneMute(state);
        Log.i(TAG,"SetMicMute");
        System.out.println("isMicrophoneMute =" + mAudioManager.isMicrophoneMute());
    }


    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub
        Log.i(TAG, "ACC::Accessibility Interrupted");
        mService = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ACC::Accessibility Shutdown");
        mService = null;
    }

    //初始化
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG, "ACC::onServiceConnected: ");
        mService = this;
        mAudioManager=(AudioManager) mService.getSystemService(Context.AUDIO_SERVICE);
        //MainActivity.this.InitafterTmConnected();
    }

    public Context getContext(){
        return getApplicationContext();
    }

//    public void startFloatingWindow(){
//                    FloatingWindow_thread=new Thread(FloatingWindow_runnable);
//                    FloatingWindow_thread.start();
//    }

//    Runnable FloatingWindow_runnable =new Runnable() {
//        @Override
//        public void run(){
//            Looper.prepare();
//            initFloatingWindow();
//            Looper.loop();
//        }
//    };
//
//    public void initFloatingWindow(){
//        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
//        FrameLayout mLayout = new FrameLayout(this);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
//        lp.format = PixelFormat.TRANSLUCENT;
//        //lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        //改成下面这句话就可以弹出输入法了
//        // lp.flags =WindowManager. LayoutParams.FLAG_NOT_TOUCH_MODAL;
//        lp.flags |=WindowManager. LayoutParams.FLAG_NOT_TOUCH_MODAL |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = Gravity.TOP;
//        LayoutInflater inflater = LayoutInflater.from(this);
//        inflater.inflate(R.layout.activity_statistic, mLayout);
//        wm.addView(mLayout, lp);
//    }




    public static boolean isStart() {
        return mService != null;
    }
}
