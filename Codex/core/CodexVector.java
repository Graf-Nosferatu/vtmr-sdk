/**
 * Vector library. 
 * <BR> No natives, 100% pure Java :-) 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/


public class CodexVector extends Codex
{
	private float v[] = new float[3];

	// ------------------------------------------------------------------------

	public String ToString()
	{
		return("<" + Float.toString(v[VEC_X]) + " / " + Float.toString(v[VEC_Y]) + " / " + Float.toString(v[VEC_Z]) + ">");
	}

	// ------------------------------------------------------------------------

	// Zero constructor
	public CodexVector()
	{
		v[VEC_X] = 0;
		v[VEC_Y] = 0;
		v[VEC_Z] = 0;
	}

	// Copy constructor
	public CodexVector(CodexVector op1)
	{
		v[VEC_X] = op1.GetX();
		v[VEC_Y] = op1.GetY();
		v[VEC_Z] = op1.GetZ();
	}

	// Set constructor
	public CodexVector(float x, float y, float z)
	{
		v[VEC_X] = x;
		v[VEC_Y] = y;
		v[VEC_Z] = z;
	}

	// Float array constructor
	public CodexVector(float[] op1)
	{
		v[VEC_X] = op1[0];
		v[VEC_Y] = op1[1];
		v[VEC_Z] = op1[2];
	}

	// ------------------------------------------------------------------------

	public void Set()
	{
		v[VEC_X] = 0;
		v[VEC_Y] = 0;
		v[VEC_Z] = 0;
	}

	public void Set(CodexVector op1)
	{
		v[VEC_X] = op1.GetX();
		v[VEC_Y] = op1.GetY();
		v[VEC_Z] = op1.GetZ();
	}

	public void Set(float x, float y, float z)
	{
		v[VEC_X] = x;
		v[VEC_Y] = y;
		v[VEC_Z] = z;
	}

	public void Set(float[] op1)
	{
		v[VEC_X] = op1[0];
		v[VEC_Y] = op1[1];
		v[VEC_Z] = op1[2];
	}

	public void SetX(float x)
	{
		v[VEC_X] = x;
	}

	public void SetX(CodexVector op1)
	{
		v[VEC_X] = op1.GetX();
	}

	public void SetX(float[] op1)
	{
		v[VEC_X] = op1[0];
	}

	public void SetY(float y)
	{
		v[VEC_Y] = y;
	}

	public void SetY(CodexVector op1)
	{
		v[VEC_Y] = op1.GetY();
	}

	public void SetY(float[] op1)
	{
		v[VEC_Y] = op1[1];
	}

	public void SetZ(float z)
	{
		v[VEC_Z] = z;
	}

	public void SetZ(CodexVector op1)
	{
		v[VEC_Z] = op1.GetZ();
	}

	public void SetZ(float[] op1)
	{
		v[VEC_Z] = op1[2];
	}

	public void SetXY(float x, float y)
	{
		v[VEC_X] = x;
		v[VEC_Y] = y;
	}

	public void SetXY(CodexVector op1)
	{
		v[VEC_X] = op1.GetX();
		v[VEC_Y] = op1.GetY();
	}

	public void SetXY(float[] op1)
	{
		v[VEC_X] = op1[0];
		v[VEC_Y] = op1[1];
	}

	public void SetYZ(float y, float z)
	{
		v[VEC_Y] = y;
		v[VEC_Z] = z;
	}
	
	public void SetYZ(CodexVector op1)
	{
		v[VEC_Y] = op1.GetY();
		v[VEC_Z] = op1.GetZ();
	}

	public void SetYZ(float[] op1)
	{
		v[VEC_Y] = op1[1];
		v[VEC_Z] = op1[2];
	}

	public void SetXZ(float x, float z)
	{
		v[VEC_X] = x;
		v[VEC_Z] = z;
	}

	public void SetXZ(CodexVector op1)
	{
		v[VEC_X] = op1.GetX();
		v[VEC_Z] = op1.GetZ();
	}

	public void SetXZ(float[] op1)
	{
		v[VEC_X] = op1[0];
		v[VEC_Z] = op1[2];
	}

	public void SetUnitX()
	{
		v[VEC_X] = 1;
		v[VEC_Y] = 0;
		v[VEC_Z] = 0;
	}

	public void SetUnitY()
	{
		v[VEC_X] = 0;
		v[VEC_Y] = 1;
		v[VEC_Z] = 0;
	}

	public void SetUnitZ()
	{
		v[VEC_X] = 0;
		v[VEC_Y] = 0;
		v[VEC_Z] = 1;
	}

	public void SetRandom()
	{
		v[VEC_X] = (float)((Math.random() - 0.5) * 2);
		v[VEC_Y] = (float)((Math.random() - 0.5) * 2);
		v[VEC_Z] = (float)((Math.random() - 0.5) * 2);
	}

	// ------------------------------------------------------------------------

	public float[] AsArray()
	{
		return(v);
	}

	public float GetX()
	{
		return(v[VEC_X]);
	}

	public float GetY()
	{
		return(v[VEC_Y]);
	}

	public float GetZ()
	{
		return(v[VEC_Z]);
	}

	// ------------------------------------------------------------------------

	public void Scale(float scale)
	{
		v[VEC_X] *= scale;
		v[VEC_Y] *= scale;
		v[VEC_Z] *= scale;
	}

	public void Scale(float scale, CodexVector op1)
	{
		v[VEC_X] = op1.GetX() * scale;
		v[VEC_Y] = op1.GetY() * scale;
		v[VEC_Z] = op1.GetZ() * scale;
	}

	public void Scale(CodexVector op1, float scale)
	{
		v[VEC_X] = op1.GetX() * scale;
		v[VEC_Y] = op1.GetY() * scale;
		v[VEC_Z] = op1.GetZ() * scale;
	}

	public void Scale(float scale, float[] op1)
	{
		v[VEC_X] = op1[0] * scale;
		v[VEC_Y] = op1[1] * scale;
		v[VEC_Z] = op1[2] * scale;
	}

	public void Scale(float[] op1, float scale)
	{
		v[VEC_X] = op1[0] * scale;
		v[VEC_Y] = op1[1] * scale;
		v[VEC_Z] = op1[2] * scale;
	}
	
	// ------------------------------------------------------------------------

	public void Neg()
	{
		v[VEC_X] = -v[VEC_Y];
		v[VEC_Y] = -v[VEC_X];
		v[VEC_Z] = -v[VEC_Z];
	}

	public void Neg(CodexVector op1)
	{
		v[VEC_X] = -op1.GetX();
		v[VEC_Y] = -op1.GetY();
		v[VEC_Z] = -op1.GetZ();
	}

	public void Neg(float[] op1)
	{
		v[VEC_X] = op1[0];
		v[VEC_Y] = op1[1];
		v[VEC_Z] = op1[2];
	}

	// ------------------------------------------------------------------------

	public void Add(CodexVector op1)
	{
		v[VEC_X] += op1.GetX();
		v[VEC_Y] += op1.GetY();
		v[VEC_Z] += op1.GetZ();
	}

	public void Add(float x, float y, float z)
	{
		v[VEC_X] += x;
		v[VEC_Y] += y;
		v[VEC_Z] += z;
	}

	public void Add(float[] op1)
	{
		v[VEC_X] += op1[0];
		v[VEC_Y] += op1[1];
		v[VEC_Z] += op1[2];
	}

	public void Add(CodexVector op1, CodexVector op2)
	{
		v[VEC_X] = op1.GetX() + op2.GetX();
		v[VEC_Y] = op1.GetY() + op2.GetY();
		v[VEC_Z] = op1.GetZ() + op2.GetZ();
	}

	public void Add(CodexVector op1, float x, float y, float z)
	{
		v[VEC_X] = op1.GetX() + x;
		v[VEC_Y] = op1.GetY() + y;
		v[VEC_Z] = op1.GetZ() + z;
	}

	public void Add(float x, float y, float z, CodexVector op4)
	{
		v[VEC_X] = op4.GetX() + x;
		v[VEC_Y] = op4.GetY() + y;
		v[VEC_Z] = op4.GetZ() + z;
	}

	public void Add(CodexVector op1, float[] op2)
	{
		v[VEC_X] = op1.GetX() + op2[0];
		v[VEC_Y] = op1.GetY() + op2[1];
		v[VEC_Z] = op1.GetZ() + op2[2];
	}

	public void Add(float[] op1, CodexVector op2)
	{
		v[VEC_X] = op2.GetX() + op1[0];
		v[VEC_Y] = op2.GetY() + op1[1];
		v[VEC_Z] = op2.GetZ() + op1[2];
	}

	public void Add(float x, float y, float z, float[] op4)
	{
		v[VEC_X] = x + op4[0];
		v[VEC_Y] = y + op4[1];
		v[VEC_Z] = y + op4[2];
	}

	public void Add(float[] op1, float x, float y, float z)
	{
		v[VEC_X] = x + op1[0];
		v[VEC_Y] = y + op1[1];
		v[VEC_Z] = y + op1[2];
	}

	public void Add(float[] op1, float[] op2)
	{
		v[VEC_X] = op2[0] + op1[0];
		v[VEC_Y] = op2[1] + op1[1];
		v[VEC_Z] = op2[2] + op1[2];
	}

	public void Add(float x, float y, float z, float x2, float y2, float z2)
	{
		v[VEC_X] = x + x2;
		v[VEC_Y] = y + y2;
		v[VEC_Z] = y + z2;
	}

	// ------------------------------------------------------------------------

	public void Sub(CodexVector op1)
	{
		v[VEC_X] -= op1.GetX();
		v[VEC_Y] -= op1.GetY();
		v[VEC_Z] -= op1.GetZ();
	}

	public void Sub(float x, float y, float z)
	{
		v[VEC_X] -= x;
		v[VEC_Y] -= y;
		v[VEC_Z] -= z;
	}

	public void Sub(float[] op1)
	{
		v[VEC_X] -= op1[0];
		v[VEC_Y] -= op1[1];
		v[VEC_Z] -= op1[2];
	}

	public void Sub(CodexVector op1, CodexVector op2)
	{
		v[VEC_X] = op1.GetX() - op2.GetX();
		v[VEC_Y] = op1.GetY() - op2.GetY();
		v[VEC_Z] = op1.GetZ() - op2.GetZ();
	}

	public void Sub(CodexVector op1, float x, float y, float z)
	{
		v[VEC_X] = op1.GetX() - x;
		v[VEC_Y] = op1.GetY() - y;
		v[VEC_Z] = op1.GetZ() - z;
	}

	public void Sub(float x, float y, float z, CodexVector op4)
	{
		v[VEC_X] = x - op4.GetX();
		v[VEC_Y] = y - op4.GetY();
		v[VEC_Z] = z - op4.GetZ();
	}

	public void Sub(CodexVector op1, float[] op2)
	{
		v[VEC_X] = op1.GetX() - op2[0];
		v[VEC_Y] = op1.GetY() - op2[1];
		v[VEC_Z] = op1.GetZ() - op2[2];
	}

	public void Sub(float[] op1, CodexVector op2)
	{
		v[VEC_X] = op1[0] - op2.GetX();
		v[VEC_Y] = op1[1] - op2.GetY();
		v[VEC_Z] = op1[2] - op2.GetZ();
	}

	public void Sub(float x, float y, float z, float[] op4)
	{
		v[VEC_X] = x - op4[0];
		v[VEC_Y] = y - op4[1];
		v[VEC_Z] = y - op4[2];
	}

	public void Sub(float[] op1, float x, float y, float z)
	{
		v[VEC_X] = op1[0] - x;
		v[VEC_Y] = op1[1] - y;
		v[VEC_Z] = op1[2] - z;
	}

	public void Sub(float[] op1, float[] op2)
	{
		v[VEC_X] = op2[0] - op1[0];
		v[VEC_Y] = op2[1] - op1[1];
		v[VEC_Z] = op2[2] - op1[2];
	}

	public void Sub(float x, float y, float z, float x2, float y2, float z2)
	{
		v[VEC_X] = x - x2;
		v[VEC_Y] = y - y2;
		v[VEC_Z] = y - z2;
	}

	// ------------------------------------------------------------------------

	public void Cross(CodexVector op1)
	{
		float v1 = v[VEC_Y] * op1.GetZ() - v[VEC_Z] * op1.GetY();
		float v2 = v[VEC_Z] * op1.GetX() - v[VEC_X] * op1.GetZ();
		float v3 = v[VEC_X] * op1.GetY() - v[VEC_Y] * op1.GetX();

		Set(v1, v2, v3);
	}

	public void Cross(float x, float y, float z)
	{
		float v1 = v[VEC_Y] * z - v[VEC_Z] * y;
		float v2 = v[VEC_Z] * x - v[VEC_X] * z;
		float v3 = v[VEC_X] * y - v[VEC_Y] * x;

		Set(v1, v2, v3);
	}

	public void Cross(float[] op1)
	{
		float v1 = v[VEC_Y] * op1[2] - v[VEC_Z] * op1[1];
		float v2 = v[VEC_Z] * op1[0] - v[VEC_X] * op1[2];
		float v3 = v[VEC_X] * op1[1] - v[VEC_Y] * op1[0];

		Set(v1, v2, v3);
	}
	
	public void Cross(CodexVector op1, CodexVector op2)
	{
		float v1 = op1.GetY() * op2.GetZ() - op1.GetZ() * op2.GetY();
		float v2 = op1.GetZ() * op2.GetX() - op1.GetX() * op2.GetZ();
		float v3 = op1.GetX() * op2.GetY() - op1.GetY() * op2.GetX();

		Set(v1, v2, v3);
	}

	public void Cross(CodexVector op1, float x, float y, float z)
	{
		float v1 = op1.GetY() * z - op1.GetZ() * y;
		float v2 = op1.GetZ() * x - op1.GetX() * z;
		float v3 = op1.GetX() * y - op1.GetY() * x;

		Set(v1, v2, v3);
	}

	public void Cross(float x, float y, float z, CodexVector op4)
	{
		float v1 = op4.GetZ() * y - op4.GetY() * z;
		float v2 = op4.GetX() * z - op4.GetZ() * x;
		float v3 = op4.GetY() * x - op4.GetX() * y;

		Set(v1, v2, v3);
	}

	public void Cross(CodexVector op1, float[] op2)
	{
		float v1 = op1.GetY() * op2[2] - op1.GetZ() * op2[1];
		float v2 = op1.GetZ() * op2[0] - op1.GetX() * op2[2];
		float v3 = op1.GetX() * op2[1] - op1.GetY() * op2[0];

		Set(v1, v2, v3);
	}

	public void Cross(float[] op1, CodexVector op2)
	{
		float v1 = op2.GetZ() * op1[1] - op2.GetY() * op1[2];
		float v2 = op2.GetX() * op1[2] - op2.GetZ() * op1[0];
		float v3 = op2.GetY() * op1[0] - op2.GetX() * op1[1];

		Set(v1, v2, v3);
	}

	public void Cross(float x, float y, float z, float[] op4)
	{
		float v1 = y * op4[2] - z * op4[1];
		float v2 = z * op4[0] - x * op4[2];
		float v3 = x * op4[1] - y * op4[0];

		Set(v1, v2, v3);
	}

	public void Cross(float[] op1, float x, float y, float z)
	{
		float v1 = z * op1[1] - y * op1[2];
		float v2 = x * op1[2] - z * op1[0];
		float v3 = y * op1[0] - x * op1[1];

		Set(v1, v2, v3);
	}

	public void Cross(float x, float y, float z, float x2, float y2, float z2)
	{
		float v1 = y * z2 - z * y2;
		float v2 = z * x2 - x * z2;
		float v3 = x * y2 - y * x2;

		Set(v1, v2, v3);
	}

	public void Cross(float[] op1, float[] op2)
	{
		float v1 = op2[2] * op1[1] - op2[1] * op1[2];
		float v2 = op2[0] * op1[2] - op2[2] * op1[0];
		float v3 = op2[1] * op1[0] - op2[0] * op1[1];

		Set(v1, v2, v3);
	}

	// ------------------------------------------------------------------------

	public boolean IsZero()
	{
		return((v[VEC_X] == (float)0.0) && (v[VEC_Y] == (float)0.0) && (v[VEC_Z] == (float)0.0));
	}

	public boolean Equals()
	{
		return((v[VEC_X] == (float)0.0) && (v[VEC_Y] == (float)0.0) && (v[VEC_Z] == (float)0.0));
	}

	public boolean Equals(CodexVector op1)
	{
		return((v[VEC_X] == op1.GetX()) && (v[VEC_Y] == op1.GetY()) && (v[VEC_Z] == op1.GetZ()));
	}

	public boolean Equals(float x, float y, float z)
	{
		return((v[VEC_X] == x) && (v[VEC_Y] == y) && (v[VEC_Z] == z));
	}

	public boolean Equals(float[] farray)
	{
		return((v[VEC_X] == farray[0]) && (v[VEC_Y] == farray[1]) && (v[VEC_Z] == farray[2]));
	}

	public static boolean Equals(CodexVector op1, CodexVector op2)
	{
		return((op1.GetX() == op2.GetX()) && (op1.GetY() == op2.GetY()) && (op1.GetZ() == op2.GetZ()));
	}

	public static boolean Equals(CodexVector op1, float x, float y, float z)
	{
		return((op1.GetX() == x) && (op1.GetY() == y) && (op1.GetZ() == z));
	}

	public static boolean Equals(float x, float y, float z, CodexVector op4)
	{
		return((op4.GetX() == x) && (op4.GetY() == y) && (op4.GetZ() == z));
	}

	public static boolean Equals(CodexVector op1, float[] op2)
	{
		return((op1.GetX() == op2[0]) && (op1.GetY() == op2[1]) && (op1.GetZ() == op2[2]));
	}

	public static boolean Equals(float[] op1, CodexVector op2)
	{
		return((op2.GetX() == op1[0]) && (op2.GetY() == op1[1]) && (op2.GetZ() == op1[2]));
	}

	public static boolean Equals(float x, float y, float z, float[] op4)
	{
		return((x == op4[0]) && (y == op4[1]) && (z == op4[2]));
	}

	public static boolean Equals(float[] op1, float x, float y, float z)
	{
		return((x == op1[0]) && (y == op1[1]) && (z == op1[2]));
	}

	public static boolean Equals(float[] op1, float[] op2)
	{
		return((op1[0] == op2[0]) && (op1[1] == op2[1]) && (op1[2] == op2[2]));
	}

	public static boolean Equals(float x, float y, float z, float x2, float y2, float z2)
	{
		return((x == x2) && (y == y2) && (z == z2));
	}

	// ------------------------------------------------------------------------

	public float Len()
	{
		return((float)Math.sqrt(v[VEC_X] * v[VEC_X] + v[VEC_Y] * v[VEC_Y] + v[VEC_Z] * v[VEC_Z]));
	}

	public static float Len(CodexVector op1)
	{
		return((float)Math.sqrt(op1.GetX() * op1.GetX() + op1.GetY() * op1.GetY() + op1.GetZ() * op1.GetZ()));
	}

	public static float Len(float x, float y, float z)
	{
		return((float)Math.sqrt(x * x + y * y + z * z));
	}

	public static float Len(float[] op1)
	{
		return((float)Math.sqrt(op1[0] * op1[0] + op1[1] * op1[1] + op1[2] * op1[2]));
	}

	// ------------------------------------------------------------------------

	public float Max()
	{
		return(Math.max(v[VEC_X], Math.max(v[VEC_Y], v[VEC_Z])));
	}

	public static float Max(CodexVector op1)
	{
		return(Math.max(op1.GetX(), Math.max(op1.GetY(), op1.GetZ())));
	}

	public static float Max(float x, float y, float z)
	{
		return(Math.max(x, Math.max(y, z)));
	}

	public static float Max(float[] op1)
	{
		return(Math.max(op1[0], Math.max(op1[1], op1[2])));
	}

	// ------------------------------------------------------------------------

	public float Dot(CodexVector op1)
	{
		return(v[VEC_X] * op1.GetX() + v[VEC_Y] * op1.GetY() + v[VEC_Z] * op1.GetZ());
	}

	public float Dot(float x, float y, float z)
	{
		return(v[VEC_X] * x + v[VEC_Y] * y + v[VEC_Z] * z);
	}

	public float Dot(float[] op1)
	{
		return(v[VEC_X] * op1[0] + v[VEC_Y] * op1[1] + v[VEC_Z] * op1[2]);
	}

	public static float Dot(CodexVector op1, CodexVector op2)
	{
		return(op1.GetX() * op2.GetX() + op1.GetY() * op2.GetY() + op1.GetZ() * op2.GetZ());
	}

	public static float Dot(CodexVector op1, float x, float y, float z)
	{
		return(op1.GetX() * x + op1.GetY() * y + op1.GetZ() * z);
	}

	public static float Dot(float x, float y, float z, CodexVector op4)
	{
		return(op4.GetX() * x + op4.GetY() * y + op4.GetZ() * z);
	}

	public static float Dot(CodexVector op1, float[] op2)
	{
		return(op1.GetX() * op2[0] + op1.GetY() * op2[1] + op1.GetZ() * op2[2]);
	}

	public static float Dot(float[] op1, CodexVector op2)
	{
		return(op2.GetX() * op1[0] + op2.GetY() * op1[1] + op2.GetZ() * op1[2]);
	}

	public static float Dot(float x, float y, float z, float[] op4)
	{
		return(x * op4[0] + y * op4[1] + z * op4[2]);
	}

	public static float Dot(float[] op1, float x, float y, float z)
	{
		return(x * op1[0] + y * op1[1] + z * op1[2]);
	}

	public static float Dot(float x, float y, float z, float x2, float y2, float z2)
	{
		return(x * x2 + y * y2 + z * z2);
	}

	public static float Dot(float[] op1, float[] op2)
	{
		return(op1[0] * op2[0] + op1[1] * op2[1] + op1[2] * op2[2]);
	}


	// ------------------------------------------------------------------------

	public float Normalize()
	{
		float len = Len();
		if(len != 0.0)
		{
			float oneOverLen = 1 / len;
			v[VEC_X] *= oneOverLen;
			v[VEC_Y] *= oneOverLen;
			v[VEC_Z] *= oneOverLen;
		}

		return(len);
	}

	public float Normalize(CodexVector op1)
	{
		float len = Len(op1);
		if(len != 0.0)
		{
			float oneOverLen = 1 / len;
			v[VEC_X] =  op1.GetX() * oneOverLen;
			v[VEC_Y] =  op1.GetY() * oneOverLen;
			v[VEC_Z] =  op1.GetZ() * oneOverLen;
		}

		return(len);
	}

	public float Normalize(float x, float y, float z)
	{
		float len = Len(x, y, z);
		if(len != 0.0)
		{
			float oneOverLen = 1 / len;
			v[VEC_X] =  x * oneOverLen;
			v[VEC_Y] =  y * oneOverLen;
			v[VEC_Z] =  z * oneOverLen;
		}

		return(len);
	}

	public float Normalize(float[] op1)
	{
		float len = Len(op1);
		if(len != 0.0)
		{
			float oneOverLen = 1 / len;
			v[VEC_X] =  op1[0] * oneOverLen;
			v[VEC_Y] =  op1[1] * oneOverLen;
			v[VEC_Z] =  op1[2] * oneOverLen;
		}

		return(len);
	}

	// ------------------------------------------------------------------------


	// CLAMP
	// RANGELIMIT
	// LERP
}