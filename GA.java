public class GA {


  public static final int MAX_GEN = 30;          //�ő吢����
  public static final int POP_SIZE = 20;          //�W�c�̃T�C�Y
  public static final int LEN_CHROM = 22;          //��`�q�̒���
  public static final double GEN_GAP = 0.2;          //�����ւ̊���
  public static final double P_MUTAION = 0.1;          //�ˑR�ψق̊m��
  public static final int RANDOM_MAX = 32767;
  public static final int BEFORE = 0;
  public static final int AFTER = 1;

  private int[][] chrom = new int[POP_SIZE][LEN_CHROM];   //���F��
  private int[] scores = new int[POP_SIZE];
  private int[] fitness = new int[POP_SIZE];            //�K���x
  private int max, min, sumfitness;         //�K���x��max,min,sum
  private int n_min;

  private boolean isGenerated = false;



  /*------------------------------------------
    �[������
   ------------------------------------------*/
  public long next = 1;

  public int rand() {
    // ��Βl�����ĕԂ�
    next = next * 1103515245 + 12345;
    return Math.abs((int)(next/65536)%32768);
  }

  public void srand(int seed) {
    next = seed;
  }


  /*------------------------------------------
    �f�[�^�\���֐�
   ------------------------------------------*/
  public void firesPopulation() {
    System.out.printf("First Population\n");
    printChromFitness();
    System.out.printf("----------------------------\n");
  }

  public void printEachChromFitness(int i) {
    int j;
    System.out.printf("[%d] ",i);
    for (j=0; j<LEN_CHROM; j++) {
      System.out.printf("%d", chrom[i][j]);
    }
    System.out.printf(": %d\n", fitness[i]);
  }

  public void printChromFitness() {
    int i;
    for (i=0; i<POP_SIZE; i++) {
      printEachChromFitness(i);
    }
  }

  public void printStatistics(int gen) {
    System.out.printf("[gen=%2d] max=%d min=%d sumfitness=%d ave=%f\n",
                  gen, max, min, sumfitness, (double)sumfitness/(double)POP_SIZE);
  }

  public void printCrossover(int flag, int parent1, int parent2, int child1, int child2, int n_cross) {
    switch (flag) {
      case BEFORE:
        System.out.printf("parent1   |"); printEachChromFitness(parent1);
        System.out.printf("parent2   |"); printEachChromFitness(parent2);
        System.out.printf("delete1   |"); printEachChromFitness(child1);
        System.out.printf("delete2   |"); printEachChromFitness(child2);
        System.out.printf("n_cross=%d\n", n_cross);
        break;
      case AFTER:
        System.out.printf("child1   |"); printEachChromFitness(child1);
        System.out.printf("child2   |"); printEachChromFitness(child2);
        System.out.printf("----------------------------\n");
        break;
    }
  }

  public void printMutation(int flag, int child, int n_mutate) {
    switch (flag) {
      case BEFORE:
        System.out.printf("child(OLD)|"); printEachChromFitness(child);
        System.out.printf("n_mutate=%d\n", n_mutate);
        break;
      case AFTER:
        System.out.printf("child(NEW)|"); printEachChromFitness(child);
        System.out.printf("----------------------------\n");
        break;
    }
  }


  /*------------------------------------------
     �ˑR�ψ�
   ------------------------------------------*/
  public void mutation(int child) {
    int n_mutate;
    double rand;

    rand = (double)rand() / ((double)(RANDOM_MAX+1));   // 0<=num<1�Ƃ���
    if (rand<P_MUTAION) {
      // �ˑR�ψوʒu
      n_mutate = rand()%LEN_CHROM;    // n_mutate=0,�E�E�E,5
      // �ˑR�ψ�
      printMutation(BEFORE, child, n_mutate);
      switch (chrom[child][n_mutate]) {
        case 0:
          chrom[child][n_mutate] = 1;
          break;
        case 1:
          chrom[child][n_mutate] = 2;
          break;
        case 2:
          chrom[child][n_mutate] = 0;
          break;
      }
      fitness[child] = objFunc(child);
      printMutation(AFTER, child, n_mutate);
    }
  }

  /*------------------------------------------
    fitness�̍��v�l�̌v�Z
   ------------------------------------------*/
  public void statistics() {
    int i;
    // �ŏ��̃X�R�A���}�C�i�X�I�I
    max = -1000;
    min = 10000;
    sumfitness = 0;

    for (i=0; i<POP_SIZE; i++) {
      if (fitness[i] > max) {
        max = fitness[i];
      }
      if (fitness[i] < min) {
        min = fitness[i];
        n_min = i;
      }
      sumfitness += fitness[i];
    }
  }


  /*------------------------------------------
     ����
   ------------------------------------------*/
  public void crossover(int parent1, int parent2, int child1, int child2) {
    int min2;
    int n_cross;
    int i,j;

    // ��ԏ������l���q���Ƃ��ăZ�b�g
    child1 = n_min;
    // 2�Ԗڂɏ������l��������
    min2 = 10000;
    for (i=0; i<POP_SIZE; i++) {
      if (i != child1) {
        if (min<=fitness[i] && fitness[i]<min2) {
          min2 = fitness[i];
          child2 = i;
        }
      }
    }

    // �����ʒu
    n_cross = rand() % (LEN_CHROM-1) + 1;   // n_cross=1,�E�E�E,9
    // ����
    printCrossover(BEFORE, parent1, parent2, child1, child2, n_cross);
    for (j=0; j<n_cross; j++) {
      chrom[child1][j] = chrom[parent1][j];
      chrom[child2][j] = chrom[parent2][j];
    }
    for (j=n_cross; j<LEN_CHROM; j++) {
      chrom[child1][j] = chrom[parent2][j];
      chrom[child2][j] = chrom[parent1][j];
    }
    fitness[child1] = objFunc(child1);
    fitness[child2] = objFunc(child2);
    printCrossover(AFTER, parent1, parent2, child1, child2, n_cross);
  }






  /*------------------------------------------
    1����̏���
   ------------------------------------------*/
  public void generation(int gen) {
    int parent1, parent2;
    int child1 = 0;
    int child2 = 0;
    int n_gen;
    int i;

    //�W�c�̕\��
    statistics();
    printStatistics(gen);

    //������
    n_gen = (int)((double)POP_SIZE * GEN_GAP/2.0);
    for (i=0; i<n_gen; i++) {
      statistics();
      parent1 = select();
      parent2 = select();
      crossover(parent1, parent2, child1, child2);
      mutation(child1);
      mutation(child2);
    }
    this.isGenerated = true;
  }


  /*------------------------------------------
    �ړI�֐��iscore��������΍����قǁI�j
   ------------------------------------------*/
  public int objFunc(int i) {
    // int j;
    // int count = 0;
    //
    // for (j=0; j<LEN_CHROM; j++) {
    //   if (chrom[i][j] == 1) {
    //     count++;
    //   }
    // }
    //
    // return count;
    return this.scores[i];
  }


  /*------------------------------------------
    �I��
   ------------------------------------------*/
  public int select() {
    // int i;
    // for (i=0; i<POP_SIZE; i++) {
    //   if (fitness[i] == max) break;
    // }
    // return i;
      int i;
      int sum;
      double rand;

      sum = 0;
      rand = (double)rand() / ((double)(RANDOM_MAX+1));   // 0<=num<1�Ƃ���

      for (i=0; i<POP_SIZE; i++) {
        sum += fitness[i];
        if ((double)sum/(double)sumfitness > rand) break;
      }
      return i;
  }

  // public int selectSecondMax() {
  //   int i;
  //   int max2 = -10000;
  //   for (i=0; i<POP_SIZE; i++) {
  //     if (fitness[i] != max) {
  //       if (max2<=fitness[i]) {
  //         max2 = fitness[i];
  //       }
  //     }
  //   }
  //   for (i=0; i<POP_SIZE; i++) {
  //     if (fitness[i] == max2) break;
  //   }
  //   return i;
  // }


  /*------------------------------------------
    �����f�[�^�ݒ�
   ------------------------------------------*/
  public void initialize() {
    int i,j;

    for (i=0; i<POP_SIZE; i++) {
      for (j=0; j<LEN_CHROM; j++) {
        chrom[i][j] = rand()%3;
      }
    }

    this.isGenerated = true;
  }


  /*------------------------------------------
     �X�R�A�̐ݒ�
   ------------------------------------------*/
  public void setScores(int[] scores, int gen) {
    for (int i=0; i<POP_SIZE; i++) {
      //todo: ojbjFunc�o�R���Ȃ��Ă����񂶂��
      this.scores[i] = scores[i];
      this.fitness[i] = objFunc(i);
    }
    // �X�R�A���Z�b�g���ꂽ��A��������܂��傤�I
    generation(gen);
  }


  /*------------------------------------------
     ��`�q��Ԃ�
   ------------------------------------------*/
   public int[][] returnGenes() {
     return this.chrom;
   }

   /*------------------------------------------
      ��`�q��Ԃ�
    ------------------------------------------*/
  public boolean returnIsGenerated() {
    return this.isGenerated;
  }

  public void changeToFalse_isGenerated() {
    this.isGenerated = false;
  }


  /*------------------------------------------
     ���C���֐�
   ------------------------------------------*/
  // public static void main(String[] args) {
  //   int gen;
  //
  //   GA ga = new GA();
  //   ga.Initialize();
  //   for (gen=1; gen<=MAX_GEN; gen++) {
  //     ga.Generation(gen);
  //   }
  // }

}