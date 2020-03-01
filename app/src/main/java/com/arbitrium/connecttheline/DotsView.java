package com.arbitrium.connecttheline;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import com.arbitrium.connecttheline.utils.DataHandler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;

@SuppressWarnings("deprecation")
public class DotsView extends View implements OnTouchListener {

	static int PossibleCells = 144;
	ArrayList<Integer> colorList = new ArrayList<Integer>();
	Hashtable<Integer, Integer> listOfColors = new Hashtable<Integer, Integer>();
	Paint paintBist;
	int[][] gridGet;
	String strBoard = null;
	Paint marginPaint;
	Paint dotpt;
	ArrayList<Point> dotPath = null;
	Hashtable<Point, Integer> pathPoints = new Hashtable<Point, Integer>();
	RoundView circleView;
	Object[][] ditMap = (Object[][]) Array.newInstance(Object.class, new int[] { 16, 16 });
	Paint patternPaint;
	HashSet<ArrayList<Point>> DotSet = new HashSet<ArrayList<Point>>();
	int cellWidth;
	int maxWidth;
	Point topLeft;
	public GameActivity gamelayer = null;
	int gridCount = 5;
	public String problemPart = null;
	Hashtable<String, ArrayList<Point>> solutionDots;
	public String solutionPart = null;
	ArrayList<Point> startPoints = new ArrayList<Point>();
	public int viewHeight;
	boolean viewOnly;
	public int viewWidth;
	private int times = 0;

	private Bitmap one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen,
			fifteen, sixteen, seventeen;
	private int theme = 1;

	public DotsView(Context context, int value, int valsed, boolean isView, int color, int theme) {
		super(context);
		this.theme = theme;
		int[] size = new int[] { 16, 16 };
		color = DataHandler.checkAndReturnColor(DataHandler.getLast_pack(), context);
		gridGet = (int[][]) Array.newInstance(Integer.TYPE, size);
		circleView = null;
		solutionDots = new Hashtable<String, ArrayList<Point>>();
		if(times==0){
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots1)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots2)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots3)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots4)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots5)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots6)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots7)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots8)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots9)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots10)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots11)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots12)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots13)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots14)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots15)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots16)));
			colorList.add(Integer.valueOf(context.getResources().getColor(R.color.dots17)));
			times =1;
		}
		marginPaint = new Paint(1);
		marginPaint.setColor(color);
		paintBist = new Paint();
		paintBist.setColor(Color.rgb(0, 0, 0));
		paintBist.setStyle(Style.FILL);
		dotpt = new Paint();
		dotpt.setColor(Color.rgb(1, 1, 1));
		dotpt.setStyle(Style.FILL);
		patternPaint = new Paint();
		patternPaint.setColor(Color.rgb(1, 1, 1));
		patternPaint.setStyle(Style.FILL);
		patternPaint.setStrokeCap(Cap.ROUND);
		viewOnly = isView;
		setOnTouchListener(this);
		if (theme == 1) {
			one = BitmapFactory.decodeResource(getResources(), R.drawable.one_one);
			two = BitmapFactory.decodeResource(getResources(), R.drawable.one_two);
			three = BitmapFactory.decodeResource(getResources(), R.drawable.one_three);
			four = BitmapFactory.decodeResource(getResources(), R.drawable.one_four);
			five = BitmapFactory.decodeResource(getResources(), R.drawable.one_five);
			six = BitmapFactory.decodeResource(getResources(), R.drawable.one_six);
			seven = BitmapFactory.decodeResource(getResources(), R.drawable.one_seven);
			eight = BitmapFactory.decodeResource(getResources(), R.drawable.one_eight);
			nine = BitmapFactory.decodeResource(getResources(), R.drawable.one_nine);
			ten = BitmapFactory.decodeResource(getResources(), R.drawable.one_ten);
			eleven = BitmapFactory.decodeResource(getResources(), R.drawable.one_eleven);
			twelve = BitmapFactory.decodeResource(getResources(), R.drawable.one_twelve);
			thirteen = BitmapFactory.decodeResource(getResources(), R.drawable.one_thirteen);
			fourteen = BitmapFactory.decodeResource(getResources(), R.drawable.one_fourteen);
			fifteen = BitmapFactory.decodeResource(getResources(), R.drawable.one_fifteen);
			sixteen = BitmapFactory.decodeResource(getResources(), R.drawable.one_sixteen);
			seventeen = BitmapFactory.decodeResource(getResources(), R.drawable.one_seventeen);
		}

		else if (theme == 2) {
			one = BitmapFactory.decodeResource(getResources(), R.drawable.two_one);
			two = BitmapFactory.decodeResource(getResources(), R.drawable.two_two);
			three = BitmapFactory.decodeResource(getResources(), R.drawable.two_three);
			four = BitmapFactory.decodeResource(getResources(), R.drawable.two_four);
			five = BitmapFactory.decodeResource(getResources(), R.drawable.two_five);
			six = BitmapFactory.decodeResource(getResources(), R.drawable.two_six);
			seven = BitmapFactory.decodeResource(getResources(), R.drawable.two_seven);
			eight = BitmapFactory.decodeResource(getResources(), R.drawable.two_eight);
			nine = BitmapFactory.decodeResource(getResources(), R.drawable.two_nine);
			ten = BitmapFactory.decodeResource(getResources(), R.drawable.two_ten);
			eleven = BitmapFactory.decodeResource(getResources(), R.drawable.two_eleven);
			twelve = BitmapFactory.decodeResource(getResources(), R.drawable.two_twelve);
			thirteen = BitmapFactory.decodeResource(getResources(), R.drawable.two_thirteen);
			fourteen = BitmapFactory.decodeResource(getResources(), R.drawable.two_fourteen);
			fifteen = BitmapFactory.decodeResource(getResources(), R.drawable.two_fifteen);
			sixteen = BitmapFactory.decodeResource(getResources(), R.drawable.two_sixteen);
			seventeen = BitmapFactory.decodeResource(getResources(), R.drawable.two_seventeen);
		}

		else if (theme == 3) {
			one = BitmapFactory.decodeResource(getResources(), R.drawable.three_one);
			two = BitmapFactory.decodeResource(getResources(), R.drawable.three_two);
			three = BitmapFactory.decodeResource(getResources(), R.drawable.three_three);
			four = BitmapFactory.decodeResource(getResources(), R.drawable.three_four);
			five = BitmapFactory.decodeResource(getResources(), R.drawable.three_five);
			six = BitmapFactory.decodeResource(getResources(), R.drawable.three_six);
			seven = BitmapFactory.decodeResource(getResources(), R.drawable.three_seven);
			eight = BitmapFactory.decodeResource(getResources(), R.drawable.three_eight);
			nine = BitmapFactory.decodeResource(getResources(), R.drawable.three_nine);
			ten = BitmapFactory.decodeResource(getResources(), R.drawable.three_ten);
			eleven = BitmapFactory.decodeResource(getResources(), R.drawable.three_eleven);
			twelve = BitmapFactory.decodeResource(getResources(), R.drawable.three_twelve);
			thirteen = BitmapFactory.decodeResource(getResources(), R.drawable.three_thirteen);
			fourteen = BitmapFactory.decodeResource(getResources(), R.drawable.three_fourteen);
			fifteen = BitmapFactory.decodeResource(getResources(), R.drawable.three_fifteen);
			sixteen = BitmapFactory.decodeResource(getResources(), R.drawable.three_sixteen);
			seventeen = BitmapFactory.decodeResource(getResources(), R.drawable.three_seventeen);
		}

	}

	private Bitmap giveBitmapFromColor(Paint paint) {
		Bitmap img = null;
		if (paint.getColor() == getResources().getColor(R.color.dots1)) {
			img = one;
		} else if (paint.getColor() == getResources().getColor(R.color.dots2)) {
			img = two;
		} else if (paint.getColor() == getResources().getColor(R.color.dots3)) {
			img = three;
		} else if (paint.getColor() == getResources().getColor(R.color.dots4)) {
			img = four;
		} else if (paint.getColor() == getResources().getColor(R.color.dots5)) {
			img = five;
		} else if (paint.getColor() == getResources().getColor(R.color.dots6)) {
			img = six;
		} else if (paint.getColor() == getResources().getColor(R.color.dots7)) {
			img = seven;
		} else if (paint.getColor() == getResources().getColor(R.color.dots8)) {
			img = eight;
		} else if (paint.getColor() == getResources().getColor(R.color.dots9)) {
			img = nine;
		} else if (paint.getColor() == getResources().getColor(R.color.dots10)) {
			img = ten;
		} else if (paint.getColor() == getResources().getColor(R.color.dots11)) {
			img = eleven;
		} else if (paint.getColor() == getResources().getColor(R.color.dots12)) {
			img = twelve;
		} else if (paint.getColor() == getResources().getColor(R.color.dots13)) {
			img = thirteen;
		} else if (paint.getColor() == getResources().getColor(R.color.dots14)) {
			img = fourteen;
		} else if (paint.getColor() == getResources().getColor(R.color.dots15)) {
			img = fifteen;
		} else if (paint.getColor() == getResources().getColor(R.color.dots16)) {
			img = sixteen;
		} else if (paint.getColor() == getResources().getColor(R.color.dots17)) {
			img = seventeen;
		}

		return img;
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = 0;
		int height = 0;
		width = bm.getWidth();
		height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	private Drawable getImageFromColor(int color) {

		Drawable res = null;
		if (theme == 1) {
			if (color == getResources().getColor(R.color.dots1)) {
				res = getResources().getDrawable(R.drawable.one_one);
			} else if (color == getResources().getColor(R.color.dots2)) {
				res = getResources().getDrawable(R.drawable.one_two);
			} else if (color == getResources().getColor(R.color.dots3)) {
				res = getResources().getDrawable(R.drawable.one_three);
			} else if (color == getResources().getColor(R.color.dots4)) {
				res = getResources().getDrawable(R.drawable.one_four);
			} else if (color == getResources().getColor(R.color.dots5)) {
				res = getResources().getDrawable(R.drawable.one_five);
			} else if (color == getResources().getColor(R.color.dots6)) {
				res = getResources().getDrawable(R.drawable.one_six);
			} else if (color == getResources().getColor(R.color.dots7)) {
				res = getResources().getDrawable(R.drawable.one_seven);
			} else if (color == getResources().getColor(R.color.dots8)) {
				res = getResources().getDrawable(R.drawable.one_eight);
			} else if (color == getResources().getColor(R.color.dots9)) {
				res = getResources().getDrawable(R.drawable.one_nine);
			} else if (color == getResources().getColor(R.color.dots10)) {
				res = getResources().getDrawable(R.drawable.one_ten);
			} else if (color == getResources().getColor(R.color.dots11)) {
				res = getResources().getDrawable(R.drawable.one_eleven);
			} else if (color == getResources().getColor(R.color.dots12)) {
				res = getResources().getDrawable(R.drawable.one_twelve);
			} else if (color == getResources().getColor(R.color.dots13)) {
				res = getResources().getDrawable(R.drawable.one_thirteen);
			} else if (color == getResources().getColor(R.color.dots14)) {
				res = getResources().getDrawable(R.drawable.one_fourteen);
			} else if (color == getResources().getColor(R.color.dots15)) {
				res = getResources().getDrawable(R.drawable.one_fifteen);
			} else if (color == getResources().getColor(R.color.dots16)) {
				res = getResources().getDrawable(R.drawable.one_sixteen);
			} else if (color == getResources().getColor(R.color.dots17)) {
				res = getResources().getDrawable(R.drawable.one_seventeen);
			}
		} else if (theme == 2) {
			if (color == getResources().getColor(R.color.dots1)) {
				res = getResources().getDrawable(R.drawable.two_one);
			} else if (color == getResources().getColor(R.color.dots2)) {
				res = getResources().getDrawable(R.drawable.two_two);
			} else if (color == getResources().getColor(R.color.dots3)) {
				res = getResources().getDrawable(R.drawable.two_three);
			} else if (color == getResources().getColor(R.color.dots4)) {
				res = getResources().getDrawable(R.drawable.two_four);
			} else if (color == getResources().getColor(R.color.dots5)) {
				res = getResources().getDrawable(R.drawable.two_five);
			} else if (color == getResources().getColor(R.color.dots6)) {
				res = getResources().getDrawable(R.drawable.two_six);
			} else if (color == getResources().getColor(R.color.dots7)) {
				res = getResources().getDrawable(R.drawable.two_seven);
			} else if (color == getResources().getColor(R.color.dots8)) {
				res = getResources().getDrawable(R.drawable.two_eight);
			} else if (color == getResources().getColor(R.color.dots9)) {
				res = getResources().getDrawable(R.drawable.two_nine);
			} else if (color == getResources().getColor(R.color.dots10)) {
				res = getResources().getDrawable(R.drawable.two_ten);
			} else if (color == getResources().getColor(R.color.dots11)) {
				res = getResources().getDrawable(R.drawable.two_eleven);
			} else if (color == getResources().getColor(R.color.dots12)) {
				res = getResources().getDrawable(R.drawable.two_twelve);
			} else if (color == getResources().getColor(R.color.dots13)) {
				res = getResources().getDrawable(R.drawable.two_thirteen);
			} else if (color == getResources().getColor(R.color.dots14)) {
				res = getResources().getDrawable(R.drawable.two_fourteen);
			} else if (color == getResources().getColor(R.color.dots15)) {
				res = getResources().getDrawable(R.drawable.two_fifteen);
			} else if (color == getResources().getColor(R.color.dots16)) {
				res = getResources().getDrawable(R.drawable.two_sixteen);
			} else if (color == getResources().getColor(R.color.dots17)) {
				res = getResources().getDrawable(R.drawable.two_seventeen);
			}
		} else if (theme == 3) {
			if (color == getResources().getColor(R.color.dots1)) {
				res = getResources().getDrawable(R.drawable.three_one);
			} else if (color == getResources().getColor(R.color.dots2)) {
				res = getResources().getDrawable(R.drawable.three_two);
			} else if (color == getResources().getColor(R.color.dots3)) {
				res = getResources().getDrawable(R.drawable.three_three);
			} else if (color == getResources().getColor(R.color.dots4)) {
				res = getResources().getDrawable(R.drawable.three_four);
			} else if (color == getResources().getColor(R.color.dots5)) {
				res = getResources().getDrawable(R.drawable.three_five);
			} else if (color == getResources().getColor(R.color.dots6)) {
				res = getResources().getDrawable(R.drawable.three_six);
			} else if (color == getResources().getColor(R.color.dots7)) {
				res = getResources().getDrawable(R.drawable.three_seven);
			} else if (color == getResources().getColor(R.color.dots8)) {
				res = getResources().getDrawable(R.drawable.three_eight);
			} else if (color == getResources().getColor(R.color.dots9)) {
				res = getResources().getDrawable(R.drawable.three_nine);
			} else if (color == getResources().getColor(R.color.dots10)) {
				res = getResources().getDrawable(R.drawable.three_ten);
			} else if (color == getResources().getColor(R.color.dots11)) {
				res = getResources().getDrawable(R.drawable.three_eleven);
			} else if (color == getResources().getColor(R.color.dots12)) {
				res = getResources().getDrawable(R.drawable.three_twelve);
			} else if (color == getResources().getColor(R.color.dots13)) {
				res = getResources().getDrawable(R.drawable.three_thirteen);
			} else if (color == getResources().getColor(R.color.dots14)) {
				res = getResources().getDrawable(R.drawable.three_fourteen);
			} else if (color == getResources().getColor(R.color.dots15)) {
				res = getResources().getDrawable(R.drawable.three_fifteen);
			} else if (color == getResources().getColor(R.color.dots16)) {
				res = getResources().getDrawable(R.drawable.three_sixteen);
			} else if (color == getResources().getColor(R.color.dots17)) {
				res = getResources().getDrawable(R.drawable.three_seventeen);
			}
		}
		return res;
	}

	protected void addPoint2Path(ArrayList<Point> list, int pointX, int pointY) {
		list.add(new Point(pointX, pointY));
		setPath(list, pointX, pointY);
	}

	protected void cutDrawingPath(int posX, int posY) {
		int number = -1;

		for (int i = 0; i < dotPath.size(); ++i) {
			Point point = (Point) dotPath.get(i);
			if (point.x == posX && point.y == posY) {
				number = i;
				break;
			}
		}

		if (number != -1) {
			int size = dotPath.size() - number;

			for (int i = 0; i < size; ++i) {
				Point point = (Point) dotPath.get(-1 + dotPath.size());
				pathPoints.remove(point);
				dotPath.remove(-1 + dotPath.size());
			}
		}

	}

	protected void cutPath(ArrayList<Point> list, int posX, int posY, boolean isThere) {
		int number = -1;
		for (int i = 0; i < list.size(); ++i) {
			Point point = (Point) list.get(i);
			if (point.x == posX && point.y == posY) {
				number = i;
				break;
			}
		}
		if (number != -1 && (isThere || number != 0)) {
			DotSet.remove(list);
			int size = list.size() - number;
			for (int i = 0; i < size; ++i) {
				removeLastPointFromPath(list);
			}
			DotSet.add(list);
		}
	}

	protected void endDraw() {
		if (dotPath != null) {
			realizeDrawingPath(dotPath);
			if (dotPath.size() >= 2) {
				Point point1 = (Point) dotPath.get(0);
				Point point2 = (Point) dotPath.get(-1 + dotPath.size());
				if ((point1.x != point2.x || point1.y != point2.y)
						&& gridGet[point1.x][point1.y] == gridGet[point2.x][point2.y] && gridGet[point1.x][point1.y] > 0
						&& !GameSaver.sharedInstance(gamelayer.getApplicationContext()).soundOff) {
					gamelayer.playsound();
				}
			}
			pathPoints.clear();
			dotPath = null;
			++gamelayer.stepMoved;
			invalidate();
			gamelayer.updatePanelStatus();
			gamelayer.updateUI();
			if (isSolved()) {
				gamelayer.levelCleared();
			}
		}
	}

	protected Point findPairStartPoint(int posX, int posY) {
		int pos = gridGet[posX][posY];
		int number = 0;
		Point point;
		while (true) {
			if (number >= startPoints.size()) {
				point = new Point(-1, -1);
				break;
			}
			point = (Point) startPoints.get(number);
			int possx = point.x;
			int possy = point.y;
			if ((possx != posX || possy != posY) && gridGet[possx][possy] == pos) {
				break;
			}
			++number;
		}
		return point;
	}

	public String dot2String(ArrayList<Point> list) {
		if (list != null && list.size() != 0) {
			StringBuilder sbBuilder = new StringBuilder();
			for (int i = 0; i < list.size(); ++i) {
				Point point = (Point) list.get(i);
				sbBuilder.append(1 + point.y);
				sbBuilder.append(":");
				sbBuilder.append(1 + point.x);
				if (i < -1 + list.size()) {
					sbBuilder.append(";");
				}
			}
			return sbBuilder.toString();
		} else {
			return "";
		}
	}

	public ArrayList<Point> formalizeDots(ArrayList<Point> list) {
		if (list != null && list.size() > 1) {
			Point p1 = (Point) list.get(0);
			Point p2 = (Point) list.get(-1 + list.size());
			if (p1.y >= p2.y && (p1.y != p2.y || p1.x >= p2.x)) {
				ArrayList<Point> listPoint = new ArrayList<Point>();
				for (int i = -1 + list.size(); i >= 0; --i) {
					listPoint.add((Point) list.get(i));
				}
				return listPoint;
			}
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ArrayList<Point> getDotsPath(int value1, int value2) {
		return value1 < 16 && value2 < 16 ? (ArrayList) ditMap[value1][value2] : null;
	}

	public int getDotCount() {
		return startPoints.size() / 2;
	}

	@SuppressWarnings("rawtypes")
	protected int getFullDotCount() {
		int i = 0;
		Iterator<ArrayList<Point>> iter = DotSet.iterator();

		while (iter.hasNext()) {
			if (isFullDots((ArrayList) iter.next())) {
				++i;
			}
		}

		return i;
	}

	protected int getNextPieColor(int num) {
		if (num < colorList.size()) {
			return ((Integer) colorList.get(num)).intValue();
		} else {
			Integer valueNum = Integer.valueOf(num);
			if (listOfColors.get(valueNum) != null) {
				return ((Integer) listOfColors.get(valueNum)).intValue();
			} else {
				Random rand = new Random();
				int color = Color.rgb(rand.nextInt(), rand.nextInt(), rand.nextInt());
				listOfColors.put(valueNum, Integer.valueOf(color));
				return color;
			}
		}
	}

	protected boolean isPathCompleted(ArrayList<Point> list) {
		int length = list.size();
		boolean isThere = false;
		if (length >= 2) {
			Point p1 = (Point) list.get(0);
			Point p2 = (Point) list.get(-1 + list.size());
			if (p1.x == p2.x) {
				int p1Y = p1.y;
				int p2Y = p2.y;
				isThere = false;
				if (p1Y == p2Y) {
					return isThere;
				}
			}

			int posArrP1 = gridGet[p1.x][p1.y];
			int posArrP2 = gridGet[p2.x][p2.y];
			isThere = false;
			if (posArrP1 == posArrP2) {
				int coordArr = gridGet[p1.x][p1.y];
				isThere = false;
				if (coordArr > 0) {
					isThere = true;
				}
			}
		}

		return isThere;
	}

	@SuppressWarnings("rawtypes")
	protected boolean isFullDots(ArrayList list) {
		int length = list.size();
		boolean isThere = false;
		if (length >= 2) {
			Point p1 = (Point) list.get(0);
			Point p2 = (Point) list.get(-1 + list.size());
			if (p1.x == p2.x) {
				int p1Y = p1.y;
				int p2Y = p2.y;
				isThere = false;
				if (p1Y == p2Y) {
					return isThere;
				}
			}

			int boardArrp1 = gridGet[p1.x][p1.y];
			int boardArrp2 = gridGet[p2.x][p2.y];
			isThere = false;
			if (boardArrp1 == boardArrp2) {
				int pos = gridGet[p1.x][p1.y];
				isThere = false;
				if (pos > 0) {
					isThere = true;
				}
			}
		}

		return isThere;
	}

	@SuppressWarnings("rawtypes")
	protected boolean isSolved() {
		int poasd = startPoints.size() / 2;
		if (getFullDotCount() < poasd) {
			return false;
		} else {
			HashSet<Point> pointSet = new HashSet<Point>();
			Iterator<ArrayList<Point>> iterList = DotSet.iterator();

			while (iterList.hasNext()) {
				ArrayList list = (ArrayList) iterList.next();

				for (int i = 0; i < list.size(); ++i) {
					pointSet.add((Point) list.get(i));
				}
			}

			if (pointSet.size() == gridCount * gridCount) {
				return true;
			} else {
				return false;
			}
		}
	}

	protected void moveDraw2Point(int start, int end) {
		if (dotPath != null && dotPath.size() > 0) {
			Point startPoint = (Point) dotPath.get(0);
			Point endPoint = (Point) dotPath.get(-1 + dotPath.size());
			if (start == endPoint.x && (end == 1 + endPoint.y || end == -1 + endPoint.y)
					|| end == endPoint.y && (start == 1 + endPoint.x || start == -1 + endPoint.x)) {
				cutDrawingPath(start, end);
				if (!isPathCompleted(dotPath)) {
					int custom = gridGet[start][end];
					if (custom == 0 || custom == gridGet[startPoint.x][startPoint.y]) {
						Point posPoint = new Point(start, end);
						dotPath.add(posPoint);
						pathPoints.put(posPoint, Integer.valueOf(1));
					}
				}

				invalidate();
			}
		}

	}

	public void moveFlagView(int posX, int posY) {
		int getX = ((LayoutParams) getLayoutParams()).x;
		int getY = ((LayoutParams) getLayoutParams()).y;
		int totX = posX + getX;
		int totY = posY + getY;
		int ratioX = ((LayoutParams) getLayoutParams()).width / gridCount;
		int ratioY = (int) Math.max(1.4D * (double) ratioX, 40.0D);
		LayoutParams params = new LayoutParams(ratioY, ratioY, totX, totY);
		circleView.setLayoutParams(params);
		circleView.updateCornerRadius((float) ratioY / 2.0F);
		if (maxWidth != ratioX) {
			maxWidth = ratioX;
		}

		if (cellWidth != ratioY) {
			cellWidth = ratioY;
		}

	}

	@SuppressWarnings("rawtypes")
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0, Mode.CLEAR);
		byte insTagram = 4;
		if (viewOnly) {
			insTagram = 2;
		}

		float width = (float) viewWidth;
		float height = (float) viewHeight;
		marginPaint.setStrokeWidth((float) insTagram);
		paintBist.setColor(getResources().getColor(R.color.background_board));
		canvas.drawPaint(paintBist);
		canvas.drawLine((float) (insTagram / 2), (float) (insTagram / 2), 1.0F + (width - (float) (insTagram / 2)),
				(float) (insTagram / 2), marginPaint);
		canvas.drawLine(width - (float) (insTagram / 2), (float) (insTagram / 2), width - (float) (insTagram / 2),
				1.0F + (height - (float) (insTagram / 2)), marginPaint);
		canvas.drawLine(width - (float) (insTagram / 2), height - (float) (insTagram / 2), (float) (-1 + insTagram / 2),
				height - (float) (insTagram / 2), marginPaint);
		canvas.drawLine((float) (insTagram / 2), height - (float) (insTagram / 2), (float) (insTagram / 2),
				(float) (-1 + insTagram / 2), marginPaint);
		float ratioF = (float) (((LayoutParams) getLayoutParams()).width / gridCount);
		int ifEleser = 0;

		while (true) {
			int count = -1 + gridCount;
			if (ifEleser >= count) {
				float extraz = ratioF * 0.3F;
				Iterator<ArrayList<Point>> iter = DotSet.iterator();

				label67: while (iter.hasNext()) {
					ArrayList listArr = (ArrayList) iter.next();
					Point pointTak = (Point) listArr.get(0);
					int post = gridGet[pointTak.x][pointTak.y];
					patternPaint.setStrokeWidth(extraz);
					int colorNext = getNextPieColor(post - 1);
					int colorNew = Color.argb(76, Color.red(colorNext), Color.green(colorNext), Color.blue(colorNext));
					paintBist.setColor(colorNew);
					int nested = 0;

					while (true) {
						int size = listArr.size();
						if (nested >= size) {
							patternPaint.setColor(colorNext);
							int isTehe = 1;

							while (true) {
								int insSize = listArr.size();
								if (isTehe >= insSize) {
									continue label67;
								}

								Point ptGet = (Point) listArr.get(isTehe - 1);
								Point ptSGet = (Point) listArr.get(isTehe);
								if (pathPoints.containsKey(ptSGet)) {
									continue label67;
								}

								canvas.drawLine(ratioF * (float) (0.5D + (double) ptGet.x),
										ratioF * (float) (0.5D + (double) ptGet.y),
										ratioF * (float) (0.5D + (double) ptSGet.x),
										ratioF * (float) (0.5D + (double) ptSGet.y), patternPaint);
								canvas.drawCircle(ratioF * (float) (0.5D + (double) ptSGet.x),
										ratioF * (float) (0.5D + (double) ptSGet.y), extraz / 2.0F - 1.0F,
										patternPaint);
								++isTehe;
							}
						}

						Point pointM = (Point) listArr.get(nested);
						canvas.drawRect(1.0F + ratioF * (float) pointM.x, 1.0F + ratioF * (float) pointM.y,
								ratioF * (float) pointM.x, ratioF + ratioF * (float) pointM.y, paintBist);
						++nested;
					}
				}

				if (dotPath != null) {
					Point newPoint = (Point) dotPath.get(0);
					int posMatrix = gridGet[newPoint.x][newPoint.y];
					patternPaint.setStrokeWidth(0.3F * ratioF);
					patternPaint.setColor(getNextPieColor(posMatrix - 1));
					patternPaint.setStrokeCap(Cap.ROUND);
					int sCount = 1;

					while (true) {
						int size = dotPath.size();
						if (sCount >= size) {
							break;
						}

						Point patyhPoint = (Point) dotPath.get(sCount - 1);
						Point pathPoint = (Point) dotPath.get(sCount);
						canvas.drawLine(ratioF * (float) (0.5D + (double) patyhPoint.x),
								ratioF * (float) (0.5D + (double) patyhPoint.y),
								ratioF * (float) (0.5D + (double) pathPoint.x),
								ratioF * (float) (0.5D + (double) pathPoint.y), patternPaint);
						canvas.drawCircle(ratioF * (float) (0.5D + (double) pathPoint.x),
								ratioF * (float) (0.5D + (double) pathPoint.y), extraz / 2.0F - 1.0F, patternPaint);
						++sCount;
					}
				}

				float ratioSet = (float) ((int) (0.7D * (double) ratioF));
				int restValur = 0;
				float diaOfDots = (float) ((int) (0.7D * (double) ratioF));

				while (true) {
					int gridCountSize = gridCount;
					if (restValur >= gridCountSize) {
						return;
					}

					int manster = 0;

					while (true) {
						int gridCo = gridCount;
						if (manster >= gridCo) {
							++restValur;
							break;
						}

						float seixeRest = (float) ((double) ratioF * (0.5D + (double) restValur));
						float seixseManster = (float) ((double) ratioF * (0.5D + (double) manster));
						if (gridGet[restValur][manster] > 0 && gridGet[restValur][manster] < PossibleCells) {
							dotpt.setColor(getNextPieColor(-1 + gridGet[restValur][manster]));
							if (theme == 0) {
								canvas.drawCircle(seixeRest, seixseManster, diaOfDots / 2, dotpt);
							} else {
								Bitmap drawBp = giveBitmapFromColor(dotpt);
								canvas.drawBitmap(getResizedBitmap(drawBp, (int) (diaOfDots), (int) (diaOfDots)),
										seixeRest - diaOfDots / 2, seixseManster - diaOfDots / 2, null);
							}

						}

						++manster;
					}
				}
			}

			canvas.drawLine(ratioF * (float) (ifEleser + 1), (float) (insTagram / 2), ratioF * (float) (ifEleser + 1),
					height, marginPaint);
			canvas.drawLine((float) (insTagram / 2), ratioF * (float) (ifEleser + 1), width,
					ratioF * (float) (ifEleser + 1), marginPaint);
			++ifEleser;
		}
	}

	protected void onSizeChanged(int iniWi, int iniHi, int fiWi, int fiHi) {
		viewWidth = iniWi;
		viewHeight = iniHi;
		topLeft = new Point(((LayoutParams) getLayoutParams()).x, ((LayoutParams) getLayoutParams()).y);
		moveFlagView(128, 24);
	}

	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (!viewOnly) {
			Point point = new Point((int) motionEvent.getRawX() - topLeft.x, (int) motionEvent.getRawY() - topLeft.y);
			int aspX = point.x / maxWidth;
			int aspY = point.y / maxWidth;
			if (aspX >= 0 && aspX < gridCount && aspY >= 0 && aspY < gridCount) {
				if (motionEvent.getAction() == 0) {
					startDrawAtPoint(aspX, aspY, point, true);
					return true;
				}

				if (motionEvent.getAction() == 2) {
					if (dotPath != null) {
						moveFlagView(point.x - cellWidth / 2, point.y - cellWidth / 2);
						moveDraw2Point(aspX, aspY);
						return true;
					}
				} else if (motionEvent.getAction() == 1 && dotPath != null) {
					circleView.setVisibility(4);
					endDraw();
					return true;
				}
			}
		}

		return true;
	}

	public boolean parseBoardData(String str) {
		String[] strArr = str.split(";");
		if (strArr.length > 1 && strArr.length % 2 == 1) {
			for (int i = 0; i < 16; ++i) {
				for (int j = 0; j < 16; ++j) {
					gridGet[i][j] = 0;
				}
			}

			startPoints.clear();
			gridCount = Integer.parseInt(strArr[0]);
			int estimate = 1;

			for (int i = 1; i < strArr.length; i += 2) {
				String[] strArrs = strArr[i].split(":");
				int xcv = -1 + Integer.parseInt(strArrs[0]);
				int sdf = -1 + Integer.parseInt(strArrs[1]);
				gridGet[sdf][xcv] = estimate;
				startPoints.add(new Point(sdf, xcv));
				String[] strArrsd = strArr[i + 1].split(":");
				int dfsg = -1 + Integer.parseInt(strArrsd[0]);
				int num = -1 + Integer.parseInt(strArrsd[1]);
				gridGet[num][dfsg] = estimate;
				startPoints.add(new Point(num, dfsg));
				++estimate;
			}

			for (int i = 0; i < 16; ++i) {
				for (int j = 0; j < 16; ++j) {
					ditMap[i][j] = null;
				}
			}

			moveFlagView(0, 0);
			if (dotPath != null) {
				dotPath.clear();
			}

			pathPoints.clear();
			DotSet.clear();
			return true;
		} else {
			return false;
		}
	}

	public boolean parseData(String string) {
		strBoard = string;
		String[] strVar = strBoard.split("=");
		problemPart = strVar[0];
		boolean isParse = parseBoardData(strVar[0]);
		boolean isSol = true;
		if (strVar.length == 2) {
			solutionPart = strVar[1];
			isSol = parseSolutionData(strVar[1]);
		} else {
			solutionDots.clear();
		}

		return isParse && isSol;
	}

	public boolean parseSolutionData(String str) {
		solutionDots.clear();
		String[] string2 = str.split("\\|");
		if (string2.length == 0) {
			return false;
		} else {
			for (int i = 0; i < string2.length; ++i) {
				ArrayList<Point> list = formalizeDots(this.string2Dot(string2[i]));
				String strss = dot2String(list);
				solutionDots.put(strss, list);
			}

			return true;
		}
	}

	protected void realizeDrawingPath(ArrayList<Point> list) {
		for (int i = 0; i < list.size(); ++i) {
			Point point = (Point) list.get(i);
			ArrayList<Point> listPoint = getDotsPath(point.x, point.y);
			if (listPoint != null && listPoint != list) {
				cutPath(listPoint, point.x, point.y, true);
			}

			setPath(list, point.x, point.y);
		}

		DotSet.add(list);
	}

	protected void removePath(ArrayList<Point> list) {
		for (int i = 0; i < list.size(); ++i) {
			Point point = (Point) list.get(i);
			setPath((ArrayList<Point>) null, point.x, point.y);
		}

		DotSet.remove(list);
		list.clear();
	}

	protected void removeLastPointFromPath(ArrayList<Point> list) {
		if (list.size() > 0) {
			Point point = (Point) list.get(-1 + list.size());
			list.remove(-1 + list.size());
			setPath((ArrayList<Point>) null, point.x, point.y);
		}

	}

	protected void setPath(ArrayList<Point> list, int value1, int value2) {
		if (value1 < 16 && value2 < 16) {
			ditMap[value1][value2] = list;
		}

	}

	public void setupDotsView() {
		circleView = new RoundView(getContext(), 15);
		AbsoluteLayout layout = (AbsoluteLayout) ((Activity) getContext()).findViewById(R.id.mainGameLayout);
		moveFlagView(0, 0);
		circleView.setVisibility(4);
		layout.addView(circleView);
	}

	@SuppressWarnings("rawtypes")
	public void showHint() {
		if (solutionDots != null && solutionDots.size() != 0) {
			Hashtable<String, ArrayList<Point>> hashTable = new Hashtable<String, ArrayList<Point>>();
			Iterator<ArrayList<Point>> iter = DotSet.iterator();

			while (iter.hasNext()) {
				ArrayList<Point> list = formalizeDots((ArrayList<Point>) iter.next());
				hashTable.put(dot2String(list), list);
			}

			ArrayList list = null;
			Enumeration<String> sol = solutionDots.keys();

			while (sol.hasMoreElements()) {
				String strsss = (String) sol.nextElement();
				ArrayList solList = (ArrayList) solutionDots.get(strsss);
				if (!hashTable.containsKey(strsss) && (list == null || solList.size() > list.size())) {
					list = solList;
				}
			}

			if (list != null) {
				simulateDrawingPath(list);
				return;
			}
		}

	}

	protected void simulateDrawingPath(@SuppressWarnings("rawtypes") ArrayList list) {
		for (int i = 0; i < list.size(); ++i) {
			Point point = (Point) list.get(i);
			if (i == 0) {
				startDrawAtPoint(point.x, point.y, new Point(0, 0), false);
			} else {
				moveDraw2Point(point.x, point.y);
			}

			if (i == -1 + list.size()) {
				endDraw();
			}
		}

	}

	protected void startDrawAtPoint(int startX, int startY, Point point, boolean isThere) {
		if (!isThere) {
			circleView.setVisibility(4);
		}

		int boardPos = gridGet[startX][startY];
		if (boardPos >= 0) {
			ArrayList<Point> list = getDotsPath(startX, startY);
			if (isThere) {
				moveFlagView(point.x - cellWidth / 2, point.y - cellWidth / 2);
			}

			if (boardPos > 0) {
				if (isThere) {
					circleView.changeColor(getNextPieColor(boardPos - 1));
					if (theme != 0)
						circleView.setBackgroundDrawable(getImageFromColor(getNextPieColor(boardPos - 1)));
				}
			} else if (list != null) {
				Point pont = (Point) list.get(0);
				int count = gridGet[pont.x][pont.y];
				if (isThere) {
					circleView.changeColor(getNextPieColor(count - 1));
					if (theme != 0)
						circleView.setBackgroundDrawable(getImageFromColor(getNextPieColor(count - 1)));
				}
			}

			if (boardPos > 0 && boardPos < PossibleCells) {
				ArrayList<Point> list2 = getDotsPath(startX, startY);
				if (list2 != null) {
					removePath(list2);
				}

				Point pont23 = findPairStartPoint(startX, startY);
				ArrayList<Point> listte = getDotsPath(pont23.x, pont23.y);
				if (listte != null) {
					removePath(listte);
				}

				Point pontsdsd = new Point(startX, startY);
				dotPath = new ArrayList<Point>();
				dotPath.add(pontsdsd);
				pathPoints.put(pontsdsd, Integer.valueOf(1));
				if (isThere) {
					circleView.setVisibility(0);
				}

				invalidate();
			} else if (list != null) {
				for (int i = 0; i < list.size(); ++i) {
					Point pont = (Point) list.get(i);
					setPath((ArrayList<Point>) null, pont.x, pont.y);
				}

				DotSet.remove(list);
				dotPath = list;
				cutDrawingPath(startX, startY);
				dotPath.add(new Point(startX, startY));

				for (int i = 0; i < dotPath.size(); ++i) {
					Point ptset = (Point) dotPath.get(i);
					pathPoints.put(ptset, Integer.valueOf(1));
				}

				if (isThere) {
					circleView.setVisibility(0);
				}

				invalidate();
				return;
			}
		}

	}

	public ArrayList<Point> string2Dot(String str) {
		ArrayList<Point> list = new ArrayList<Point>();
		String[] strArr = str.split(";");

		for (int i = 0; i < strArr.length; ++i) {
			String[] splitStr = strArr[i].split(":");
			int index = -1 + Integer.parseInt(splitStr[0]);
			list.add(new Point(-1 + Integer.parseInt(splitStr[1]), index));
		}

		return list;
	}
}
