import { use } from 'echarts/core';
import { BarChart, PieChart } from 'echarts/charts';
import { GridComponent, TooltipComponent } from 'echarts/components';

use([BarChart, PieChart, GridComponent, TooltipComponent]);

export { default } from '../../components/EChart';
