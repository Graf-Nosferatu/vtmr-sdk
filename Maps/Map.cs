using Sdk.Templates;
using Sdk.Treasures;

namespace Sdk.Maps;

public partial class Map
{
	public Template[]? Templates { get; init; }

	public TreasureClass[]? TreasureClasses { get; init; }
}