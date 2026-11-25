import React, { useEffect, useRef } from 'react';
import { init, use } from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';

use([CanvasRenderer]);

export default function EChart({ option, label, height = 320 }) {
  const element = useRef(null);
  const instance = useRef(null);
  useEffect(() => {
    const chart = init(element.current, null, { renderer: 'canvas' });
    instance.current = chart;
    const observer = new ResizeObserver(() => chart.resize());
    observer.observe(element.current);
    return () => { observer.disconnect(); chart.dispose(); instance.current = null; };
  }, []);
  useEffect(() => { instance.current?.setOption(option, { notMerge: true }); }, [option]);
  return <div ref={element} style={{ height, width: '100%' }} role="img" aria-label={label} />;
}
