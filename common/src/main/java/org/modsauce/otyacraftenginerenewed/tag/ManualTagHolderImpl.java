package org.modsauce.otyacraftenginerenewed.tag;

import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Internal
public class ManualTagHolderImpl<T> implements ManualTagHolder<T> {
    private static final List<ManualTagHolderImpl<?>> GENERATED = new ArrayList<>();
    private final TagKey<T> tagKey;
    private final Consumer<IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<T>> tagRegister;

    public ManualTagHolderImpl(TagKey<T> tagKey, Consumer<IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<T>> tagRegister) {
        this.tagKey = tagKey;
        this.tagRegister = tagRegister;
    }

    public ManualTagHolderImpl(TagKey<T> tagKey) {
        this(tagKey, null);
    }

    @Override
    public @NotNull TagKey<T> getKey() {
        return tagKey;
    }

    @Override
    public void registering(@NotNull IntrinsicHolderTagsProviderWrapper.IntrinsicTagProviderAccess<T> tagProviderAccess) {
        if (GENERATED.contains(this)) return;
        GENERATED.add(this);

        if (tagRegister != null)
            tagRegister.accept(tagProviderAccess.tag(getKey()));
    }
}
