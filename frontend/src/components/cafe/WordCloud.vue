<template>
  <div id="word-cloud"></div>
</template>

<script>
import { getReviewWord } from '../../api/review'
export default {
  name: "WordCloud",
  data() {
    return {
      words: [],
    }
  },
  methods: {
    genLayout() {
      const cloud = require('d3-cloud');
      cloud()
        .words(this.words)
        .padding(1)
        .font('Impact')
        .fontSize(function (d) {
          return d.size;
        })
        .on('end', this.end)
        .spiral('archimedean')
        .start()
        .stop();
    },
    randomRGB() {
      const r = parseInt(Math.random() * 255);
      const g = parseInt(Math.random() * 255);
      const b = parseInt(Math.random() * 255);
      return `rgb(${r}, ${g}, ${b})`;
    },
    end(words) {
      const d3 = require('d3');
      const width = 400;
      const height = 300;
      d3.select('#word-cloud')
        .append('svg')
        .attr('width', width)
        .attr('height', height)
        .style('background', 'white')
        .append('g')
        .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')') // g를 중심에서 단어들을 그리기 때문에 g를 svg 중심으로 이동
        .selectAll('text')
        .data(words)
        .enter()
        .append('text')
        .style('font-size', (d) => {
          return d.size + 'px';
        })
        .style('font-family', 'Impact')
        .style('fill', (d) => this.randomRGB(d))
        .attr('text-anchor', 'middle')
        .attr('transform', (d) => {
          return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')';
        })
        .text((d) => d.text);
    
    },
    async wordItems() {
      try {
        const cafeId = this.$route.params.id
        await getReviewWord(cafeId).then((res) => {
          // console.log(res)
          for (let key of Object.keys(res.data)) {
            this.words.push({text: key, size: res.data[key]*10})
          }
          this.genLayout()
          // console.log(this.words)
        }) 
      } catch (error) {
        console.error(error)
      }
    }
  },
  created() {
    this.wordItems()
  }
}
</script>

<style>

</style>