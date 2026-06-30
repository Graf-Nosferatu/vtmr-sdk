using System;
using System.Collections;
using System.Collections.Generic;

namespace Sdk;

public record class File<T>( String FileName ) : IEnumerable<T>
{
	public List<T> Content { get; } = [];


	public void Fill( params T[] content )
	{
		Content.AddRange( content );
	}


	public IEnumerator<T> GetEnumerator()
	{
		return Content.GetEnumerator();
	}


	IEnumerator IEnumerable.GetEnumerator()
	{
		return GetEnumerator();
	}
}