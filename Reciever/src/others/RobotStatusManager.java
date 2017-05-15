package others;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;


public class RobotStatusManager implements ICaDSEV3RobotStatusListener,ICaDSEV3RobotFeedBackListener {

	@Override
	public void giveFeedbackByJSonTo(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusMessage(JSONObject arg0) {
		// TODO Auto-generated method stub
		
	}

}
