package org.test4j.hamcrest.iassert.object.impl;

import java.util.Collection;

import org.test4j.hamcrest.iassert.common.impl.AllAssert;
import org.test4j.hamcrest.iassert.object.intf.ICollectionAssert;

@SuppressWarnings("rawtypes")
public class CollectionAssert extends AllAssert<Collection, ICollectionAssert> implements ICollectionAssert {

	public CollectionAssert() {
		super(ICollectionAssert.class);
		this.valueClaz = Collection.class;
	}

	public CollectionAssert(Collection value) {
		super(value, ICollectionAssert.class);
		this.valueClaz = Collection.class;
	}
}
