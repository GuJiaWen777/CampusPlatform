<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数 </div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.user}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-takeaway-box" /> 发布任务数 </div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.task}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-monitor" /> 信息发布数 </div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.info}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" /> 奖励总数 </div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.reward}}
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 450px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {
      map:{}
    }
  },
  mounted() {  // 页面元素渲染之后再触发
    var option = {
      title: {
        text: '各季度用户注册数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度", "第二季度", "第三季度", "第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "本平台",
          data: [],
          type: 'bar'
        },
        {
          name: "本平台",
          data: [],
          type: 'line'
        }
      ]
    };

    // 饼图

    var pieOption = {
      title: {
        text: '各季度用户发布任务数量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: "本平台",
          type: 'pie',
          radius: '55%',
          center: ['50%', '70%'],
          label:{            //饼图图形上的文本标签
              show:true,
              position:'inner', //标签的位置
              fontWeight : 300 ,
              fontSize : 14,    //文字的字体大小
              color: "#fff",
              formatter:'{c}({d}%)'
          },
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ],

    };


    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/echarts/members").then(res => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data

//    option.series[2].data = [5,6,7,8]
//    option.series[3].data = [5,6,7,8]
//     数据准备完毕之后再set
      myChart.setOption(option);

    })
    this.request.get("/echarts/statistics").then(res => {
      this.map = res.data
    })
    this.request.get("/echarts/tasks").then(res => {
      pieOption.series[0].data = [
        {name: "第一季度", value: res.data[0]},
        {name: "第二季度", value: res.data[1]},
        {name: "第三季度", value: res.data[2]},
        {name: "第四季度", value: res.data[3]},
      ]
      pieChart.setOption(pieOption)
    })
  }
}
</script>

<style scoped>

</style>