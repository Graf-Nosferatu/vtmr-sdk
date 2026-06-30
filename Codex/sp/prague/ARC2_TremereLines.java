/**
 * Ardan Chantry 2 Tremere lines script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC2_TremereLines extends Codex
{
	private int _lineNum;

	public static String _params[] = {"Line number (1 to 3)"};

	public ARC2_TremereLines(int lineNum)
	{
		_lineNum = lineNum;
	}

	public void awaken(int guid, int causeID, int captureID)
	{
		CodexActor tremereActor = new CodexActor(guid);

		switch(_lineNum)
		{
			case 1:
				// line
				break;

			case 2:
				// line
				break;

			case 3:
				// line
				break;
		}
	}

}

