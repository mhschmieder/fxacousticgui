/**
 * MIT License
 *
 * Copyright (c) 2020, 2023 Mark Schmieder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the FxAcousticsGui Library
 *
 * You should have received a copy of the MIT License along with the
 * FxAcousticsGui Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxacousticsgui
 */
package com.mhschmieder.fxacousticsgui.control;

import com.mhschmieder.commonstoolkit.util.ClientProperties;
import com.mhschmieder.fxacousticsgui.layout.DitheringPane;
import com.mhschmieder.fxacousticsgui.layout.SplRangePane;
import com.mhschmieder.fxguitoolkit.control.ControlFactory;
import com.mhschmieder.fxguitoolkit.control.DoubleEditor;

import javafx.scene.control.Spinner;

/**
 * This is a utility class for making custom controls for Acoustics.
 */
public final class AcousticsControlFactory {

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private AcousticsControlFactory() {}

    public static Spinner< Integer > getSplRangeSpinnerInstance( final ClientProperties clientProperties,
                                                                 final boolean applyToolkitCss,
                                                                 final boolean useExtendedRange ) {
        final int minimumSplRangeDb = useExtendedRange ? 3 : 42;
        final int maximumSplRangeDb = useExtendedRange ? 120 : 72;
        final int splRangeIncrementDb = 3;
        final int defaultSplRangeDb = SplRangePane.SPL_RANGE_DB_DEFAULT;

        final String numericFormatterPattern = "##0"; //$NON-NLS-1$

        // Try to limit the size as this control can get too wide.
        final double maximumSpinnerWidth = 90d;

        // Return the fully initialized SPL Range Spinner.
        final String valueDescriptor = "an SPL range"; //$NON-NLS-1$
        final Spinner< Integer > splRangeSpinner = ControlFactory
                .makeIntegerSpinner( clientProperties,
                                     applyToolkitCss,
                                     valueDescriptor,
                                     minimumSplRangeDb,
                                     maximumSplRangeDb,
                                     defaultSplRangeDb,
                                     splRangeIncrementDb,
                                     false,
                                     numericFormatterPattern,
                                     " dB", //$NON-NLS-1$
                                     maximumSpinnerWidth );

        return splRangeSpinner;
    }

    public static Spinner< Double > getDitheringAmountSpinnerInstance( final ClientProperties clientProperties,
                                                                       final boolean applyToolkitCss ) {
        // NOTE: The number formatter knows how to deal with percentages.
        final double minimumDitheringAmount = 0.0d;
        final double maximumDitheringAmount = 0.15d;
        final double ditheringAmountIncrement = 0.005d;
        final double defaultDitheringAmount = DitheringPane.DITHERING_AMOUNT_DEFAULT;

        final String numericFormatterPattern = "##0.#"; //$NON-NLS-1$

        // Try to limit the size as this control can get too wide.
        final double maximumSpinnerWidth = 90d;

        // Return the fully initialized Dithering Amount Spinner.
        // TODO: Switch to a handcrafted percentile spinner (see examples).
        final String valueDescriptor = "amount to dither individual loudspeakers"; //$NON-NLS-1$
        final Spinner< Double > ditheringAmountSpinner = ControlFactory
                .makeDoubleSpinner( clientProperties,
                                    applyToolkitCss,
                                    valueDescriptor,
                                    minimumDitheringAmount,
                                    maximumDitheringAmount,
                                    defaultDitheringAmount,
                                    ditheringAmountIncrement,
                                    true,
                                    numericFormatterPattern,
                                    " %", //$NON-NLS-1$
                                    maximumSpinnerWidth );

        return ditheringAmountSpinner;
    }

    // This is a helper method to get a stand-alone Bandwidth Editor.
    public static DoubleEditor getBandwidthEditor( final ClientProperties clientProperties,
                                                   final double minimumValue,
                                                   final double maximumValue,
                                                   final double initialValue ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        // Declare value increment/decrement amount for up and down arrow keys.
        final double valueIncrement = 0.01d;

        final DoubleEditor bandwidthEditor = new DoubleEditor( clientProperties,
                                                               initialText,
                                                               null,
                                                               true,
                                                               0,
                                                               2,
                                                               0,
                                                               4,
                                                               minimumValue,
                                                               maximumValue,
                                                               0.0d,
                                                               valueIncrement );

        return bandwidthEditor;
    }

    // Helper method to get a stand-alone Delay Editor.
    public static DoubleEditor getDelayEditor( final ClientProperties clientProperties,
                                               final String measurementUnitString,
                                               final double minimumValue,
                                               final double maximumValue,
                                               final double initialValue ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        // Declare value increment/decrement amount for up and down arrow keys.
        final double valueIncrementMs = 0.1d;

        final DoubleEditor delayEditor = new DoubleEditor( clientProperties,
                                                           initialText,
                                                           null,
                                                           true,
                                                           0,
                                                           2,
                                                           0,
                                                           4,
                                                           minimumValue,
                                                           maximumValue,
                                                           0.0d,
                                                           valueIncrementMs );

        delayEditor.setMeasurementUnitString( measurementUnitString );

        return delayEditor;
    }

    // Helper method to get a standalone Frequency Editor.
    public static FrequencyEditor getFrequencyEditor( final ClientProperties clientProperties,
                                                      final String tooltipText,
                                                      final String measurementUnitString,
                                                      final double minimumValue,
                                                      final double maximumValue,
                                                      final double initialValue,
                                                      final double pPrecisionCutoffFrequencyHz,
                                                      final int pNumberOfDecimalPlaces ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        final FrequencyEditor frequencyEditor = new FrequencyEditor( clientProperties,
                                                                     initialText,
                                                                     tooltipText,
                                                                     minimumValue,
                                                                     maximumValue,
                                                                     initialValue,
                                                                     pPrecisionCutoffFrequencyHz,
                                                                     pNumberOfDecimalPlaces );

        frequencyEditor.setMeasurementUnitString( measurementUnitString );

        return frequencyEditor;
    }

    // This is a helper method to get a standalone Gain Editor.
    public static GainEditor getGainEditor( final ClientProperties clientProperties,
                                            final String measurementUnitString,
                                            final double gainMinimumDb,
                                            final double gainMaximumDb,
                                            final double gainDefaultDb,
                                            final boolean defaultToNegativeGain ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( gainDefaultDb );

        final GainEditor gainEditor = new GainEditor( clientProperties,
                                                      initialText,
                                                      null,
                                                      gainMinimumDb,
                                                      gainMaximumDb,
                                                      gainDefaultDb,
                                                      defaultToNegativeGain );

        gainEditor.setMeasurementUnitString( measurementUnitString );

        return gainEditor;
    }

}
