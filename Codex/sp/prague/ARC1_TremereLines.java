/**
 * Ardan Chantry 1 Tremere lines script.
 * <BR>Copyright (c) Nihilistic Software, Inc. 1998-1999
 *
*/

public class ARC1_TremereLines extends Codex
{
	private int _lineNum;

	public static String _params[] = {"Line number (1 or 2)"};

	public ARC1_TremereLines(int lineNum)
	{
		_lineNum = lineNum;
	}

	public void awaken(int guid, int causeID, int captureID)
	{
		CodexActor tremereActor = new CodexActor(guid);

		switch(_lineNum)
		{
			case 1:
				
				break;

			case 2:

				break;
		}
	}
}

