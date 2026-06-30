namespace Sdk;

public record struct MinMax<T>( T Min, T Max );

public record struct MinMaxCurrent<T>( T Min, T Max, T Current );