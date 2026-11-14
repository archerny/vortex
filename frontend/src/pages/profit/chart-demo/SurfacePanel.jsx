import React, { useEffect, useMemo, useRef, useState } from 'react';
import { Alert, Button, Segmented, Slider, Tag } from 'antd';
import { ReloadOutlined } from '@ant-design/icons';
import Plotly from 'plotly.js-gl3d-dist-min';
import { makeSurface } from './mockData';

function supportsWebGL() {
  try {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('webgl2') || canvas.getContext('webgl');
    if (!context) return false;
    context.getExtension('WEBGL_lose_context')?.loseContext();
    return true;
  } catch { return false; }
}

function SurfaceChart({ figure }) {
  const host = useRef(null);
  const plot = useRef(null);
  const latest = useRef(figure);
  const [ready, setReady] = useState(false);
  const [failed, setFailed] = useState(false);
  latest.current = figure;
  useEffect(() => {
    let disposed = false;
    let initialized = false;
    const node = document.createElement('div');
    node.style.width = '100%';
    host.current.appendChild(node);
    plot.current = node;
    const frame = requestAnimationFrame(() => {
      Plotly.newPlot(node, latest.current.data, latest.current.layout, { responsive: true, displaylogo: false, displayModeBar: true, scrollZoom: true, modeBarButtonsToRemove: ['toImage'] })
        .then(() => { initialized = true; if (!disposed) setReady(true); })
        .catch(() => { if (!disposed) setFailed(true); });
    });
    const observer = new ResizeObserver(() => {
      if (initialized && !disposed) Plotly.Plots.resize(node).catch(() => {});
    });
    observer.observe(host.current);
    return () => {
      disposed = true; cancelAnimationFrame(frame); observer.disconnect();
      Plotly.purge(node); node.remove(); plot.current = null;
    };
  }, []);
  useEffect(() => {
    if (ready && plot.current) {
      let active = true;
      Plotly.react(plot.current, figure.data, figure.layout).catch(() => { if (active) setFailed(true); });
      return () => { active = false; };
    }
  }, [figure, ready]);
  return <>
    {failed && <Alert type="error" showIcon message="三维图渲染失败" description="请确认浏览器已启用 WebGL，然后重试。" />}
    <div ref={host} className="demo-surface-host" role="img" aria-label="可旋转的合成三维曲面" />
  </>;
}

export default function SurfacePanel({ symbol }) {
  const [mode, setMode] = useState('iv');
  const [skew, setSkew] = useState(1);
  const [reset, setReset] = useState(0);
  const webgl = useMemo(supportsWebGL, [reset]);
  const surface = useMemo(() => makeSurface(symbol, mode, skew), [symbol, mode, skew]);
  const unit = mode === 'iv' ? '%' : 'USD';
  const values = surface.z.flat();
  const figure = useMemo(() => ({
    data: [{
      type: 'surface', x: surface.x, y: surface.y, z: surface.z,
      colorscale: mode === 'iv' ? [[0, '#8ed6d0'], [0.35, '#50a4bd'], [0.65, '#4a75ba'], [1, '#574a99']] : [[0, '#cf7876'], [0.45, '#f3e5cc'], [0.65, '#a3cfce'], [1, '#407c9c']],
      colorbar: { title: { text: mode === 'iv' ? 'IV (%)' : '收益 ($)' }, thickness: 12, len: 0.65, tickfont: { size: 11 } },
      contours: { z: { show: true, usecolormap: true, highlightcolor: '#345b9c', project: { z: true } } },
      hovertemplate: `${mode === 'iv' ? '行权价' : '标的价格'} $%{x:.2f}<br>剩余 %{y} 天<br>${mode === 'iv' ? 'IV' : '示意收益'} %{z:.2f} ${mode === 'iv' ? '%' : 'USD'}<extra></extra>`,
    }],
    layout: {
      autosize: true, height: 535, margin: { l: 10, r: 15, t: 20, b: 15 },
      paper_bgcolor: '#fff', font: { family: 'Arial, sans-serif', size: 12, color: '#53657e' }, uirevision: `${mode}-${reset}`,
      scene: {
        xaxis: { title: { text: mode === 'iv' ? '行权价 ($)' : '标的价格 ($)' }, gridcolor: '#e5ebf3', zeroline: false },
        yaxis: { title: { text: '到期剩余（天）' }, gridcolor: '#e5ebf3' },
        zaxis: { title: { text: mode === 'iv' ? 'IV (%)' : '示意收益 ($)' }, gridcolor: '#e5ebf3' },
        camera: { eye: { x: 1.5, y: 1.55, z: 1.05 } }, aspectratio: { x: 1.35, y: 1.1, z: 0.75 },
      },
    },
  }), [surface, mode, reset]);

  return <section>
    <div className="demo-panel-heading"><div><h2>给波动率增加一个维度</h2><p>旋转、缩放和悬停，沿行权价与期限探索整张曲面。</p></div><Tag>Plotly.js</Tag></div>
    <div className="demo-metric-grid">
      <div><span>曲面类型</span><strong className="demo-text-metric">{mode === 'iv' ? '隐含波动率' : '策略收益示意'}</strong><small>{symbol} · 合成样本</small></div>
      <div><span>最低值</span><strong>{Math.min(...values).toFixed(1)}<em>{unit}</em></strong><small>当前合成曲面范围</small></div>
      <div><span>最高值</span><strong>{Math.max(...values).toFixed(1)}<em>{unit}</em></strong><small>当前合成曲面范围</small></div>
      <div><span>采样规模</span><strong>{surface.x.length * surface.y.length}<em>点</em></strong><small>{surface.x.length} 个价格 × {surface.y.length} 个期限</small></div>
    </div>
    <div className="demo-chart-card">
      <div className="demo-toolbar"><Segmented aria-label="三维曲面类型" value={mode} onChange={setMode} options={[{ label: '波动率曲面', value: 'iv' }, { label: '收益曲面', value: 'payoff' }]} /><Button icon={<ReloadOutlined />} onClick={() => setReset((v) => v + 1)}>重置视角</Button></div>
      {mode === 'iv' && <div className="demo-surface-control"><span>偏斜强度</span><Slider aria-label="波动率偏斜强度" min={-2} max={2} step={0.1} value={skew} onChange={setSkew} style={{ width: 210, margin: '0 16px' }} /><b>{skew.toFixed(1)}</b></div>}
      {webgl ? <SurfaceChart key={reset} figure={figure} /> : <div className="demo-webgl-fallback"><Alert type="warning" showIcon message="当前浏览器未提供 WebGL" description="启用浏览器图形加速后，可在此查看和旋转三维曲面。其余三个演示面板仍可使用。" action={<Button onClick={() => setReset((v) => v + 1)}>重试</Button>} /></div>}
      <div className="demo-chart-caption">拖动旋转 · 滚轮缩放 · 工具栏平移 · 悬停读取数值</div>
    </div>
  </section>;
}
