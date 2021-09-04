package mobi.prizer.theorem_reach;

import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import theoremreach.com.theoremreach.TheoremReach;
import theoremreach.com.theoremreach.TheoremReachMomentListener;
import theoremreach.com.theoremreach.TheoremReachRewardListener;
import theoremreach.com.theoremreach.TheoremReachSurveyListener;

/** TheoremReachPlugin */
public class TheoremReachPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private MethodChannel channel;
  private FlutterPluginBinding binding = null;
  private Activity activity = null;
  TheoremReach th;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    channel = new MethodChannel(binding.getBinaryMessenger(), "theorem_reach");
    channel.setMethodCallHandler(this);
    this.binding = binding;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {
    this.activity = null;
  }

  private void init(final String apiKey, final String userId, Activity activity){
    TheoremReach.initWithApiKeyAndUserIdAndActivityContext(apiKey, userId, activity);
    TheoremReach.getInstance().setTheoremReachSurveyListener(new TheoremReachSurveyListener() {
      @Override
      public void onRewardCenterClosed() {
        channel.invokeMethod("rewardCenterClosed", null);
      }

      @Override
      public void onRewardCenterOpened() {
        channel.invokeMethod("rewardCenterOpened", null);
      }
    });
  }

  private void show(){
    TheoremReach.getInstance().showRewardCenter();
  }

  private void extractParams(MethodCall call, Activity activity){
    final String apiKey = call.argument("apiKey");
    final String userId = call.argument("userId");

    this.init(apiKey, userId, activity);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if(call.method.equals("init")){
      if(activity != null){
        this.extractParams(call, activity);
      }
    }
    if(call.method.equals("show")){
      this.show();
    }
  }
}
