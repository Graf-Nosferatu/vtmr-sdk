/**
 * Handles the link from Codex to an internal game collision. 
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-2000
 *
*/


public class CodexCollision extends Codex
{
	private static native int Create();

	

// you MUST call this after finishing with the collision. I repeat: you **MUST** !!!
// If you don't, then you'll run out of collision handles, don't come back to me crying :-)

	public native void Free();

	public native void IgnoreAll();
	public native void AcceptType(int thingType);

	public native int  GetNumResults();
	public native int  GetResult(int resultNum);

	public native int  ThingsInSphere(float [] center, float radius, int referenceThingGuid);




	// ------------------------------------------------------------------------
	// CONSTRUCTORS
	// ------------------------------------------------------------------------

	// inherit it...
	public CodexCollision()
	{
		super();
		guid = Create();
	}

	// this is a piece of crap...
	//public void finalize()
	//{
	//	Free();
	//	super.finalize();
	//}

	// ------------------------------------------------------------------------
	// NON NATIVES
	// ------------------------------------------------------------------------

}

