# ToggleEditTextView :pencil:
Easily switch between EditText and TextView seamlessly.

![ToggleEditTextView](/assets/preview.gif)

## Grab via Gradle :coffee:
```groovy
dependencies {
    implementation 'com.camerash:toggleedittextview:0.1.0'
}
```

## Usage :computer:

### ToggleEditTextView
In your layout:
```xml
<com.camerash.toggleedittextview.ToggleEditTextView
        android:id="@+id/tetv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/name"
        android:maxLines="1"
        app:tetv_textViewColor="@color/colorPrimary"
        app:tetv_editTextViewColor="@color/colorPrimaryDark"
        app:tetv_editTextBottomLineColor="@color/colorAccent" />
```
Available properties:
* `android:textSize`
* `android:hint`
* `android:inputType`
* `android:minLines`
* `android:maxLines`
* `app:tetv_editing` - Initial state of ToggleEditTextView
* `app:tetv_textViewColor` - Text color of TextView
* `app:tetv_editTextViewColor` - Text color of EditText
* `app:tetv_editTextBottomLineColor` - Bottom line's color of EditText

Available methods:
* `set/getEditing()` - Control editing state of ToggleEditTextView
* `set/getText()` - Set/Get text of ToggleEditTextView
* `set/getHint()` - Set/Get hint of ToggleEditTextView
* `set/getTextSize()` - Set/Get text size of ToggleEditTextView
* `set/getTextViewColor()` - Set/Get color of text of the TextView in ToggleEditTextView
* `set/getEditTextColor()` - Set/Get color of text of the EditText in ToggleEditTextView
* `setEditTextBottomLineColor(Int)` - Set color of bottom line of the EditText in ToggleEditTextView
* `set/getEditTextEnabled()` - Set/Get state of the EditText in ToggleEditTextView
* `set/getInputType()` - Set/Get inputType of the EditText in ToggleEditTextView
* `setMinLines()` - Set minLines of ToggleEditTextView
* `setMaxLines()` - Set maxLines of ToggleEditTextView

---

### ToggleEditButton - Button controller of ToggleEditTextViews

In your layout:
```xml
<com.camerash.toggleedittextview.ToggleEditButton
        android:id="@+id/toggleEditButton"
        android:layout_width="36dp"
        android:layout_height="36dp"
        app:teb_animationOffset="100"
        app:teb_edit="false"
        app:teb_tint="@color/colorAccent" />
```
Available properties:
* `app:teb_edit` - Initial state of ToggleEditButton (Overrides the state of the controlling ToggleEditTextViews)
* `app:teb_tint` - Tint color of the icon
* `app:teb_animationOffset` - Offset duration of fading animation between each ToggleEditTextView controlled by the ToggleEditButton

Available methods:
* `bind(vararg ToggleEditTextView)` - Bind multiple ToggleEditTextViews to the button
* `unbind(ToggleEditTextView)` - Unbind ToggleEditTextView from the button
* `unbindAll()` - Unbind all ToggleEditTextViews from the button
* `set/getAnimationOffset()` - Set/Get the Offset duration of fading animation between each ToggleEditTextView controlled by the ToggleEditButton
* `setOnClickListener(OnClickListener)` - Works the same as a good o' button

## Common usage :bell:
Kotlin:
```kotlin
val tetv1 = findViewById<ToggleEditTextView>(R.id.tetv1)
val tetv2 = findViewById<ToggleEditTextView>(R.id.tetv2)
val teb = findViewById<ToggleEditButton>(R.id.teb)
teb.bind(tetv1, tetv2)
```

Java:
```java
ToggleEditTextView tetv1 = findViewById(R.id.tetv1);
ToggleEditTextView tetv2 = findViewById(R.id.tetv2);
ToggleEditButton teb = findViewById(R.id.teb);
teb.bind(tetv1, tetv2);
```

## Sample :closed_book:
Sample app is under `/sample` directory :tada:

## License :page_with_curl:
```
MIT License

Copyright (c) 2018 Camerash

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
