import React, { useEffect, useRef } from 'react';
import * as echarts from 'echarts/core';
import { HeatmapChart, LineChart } from 'echarts/charts';
import { AriaComponent, DataZoomComponent, GridComponent, LegendComponent, MarkLineComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([LineChart, HeatmapChart, GridComponent, TooltipComponent, LegendComponent, DataZoomComponent, VisualMapComponent, MarkLineComponent, AriaComponent, CanvasRenderer]);

export default function EChart({ option, label, height = 320 }) {
  const element = useRef(null);
  const instance = useRef(null);
  useEffect(() => {
    const chart = echarts.init(element.current, null, { renderer: 'canvas' });
    instance.current = chart;
    const observer = new ResizeObserver(() => chart.resize());
    observer.observe(element.current);
    return () => { observer.disconnect(); chart.dispose(); instance.current = null; };
  }, []);
  useEffect(() => { instance.current?.setOption(option, { notMerge: true }); }, [option]);
  return <div ref={element} style={{ height, width: '100%' }} role="img" aria-label={label} />;
}
