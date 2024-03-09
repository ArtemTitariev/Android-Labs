package com.example.lr6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lr6.databinding.NoteListFragmentBinding

class NoteListFragment : Fragment() {

    lateinit var binding: NoteListFragmentBinding
    private var noteAdapter: NoteAdapter? = null
//    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoteListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ініціалізувати RecyclerView та адаптер
        noteAdapter = NoteAdapter()


        noteAdapter?.submitList(getNotes())

        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    inner class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

        private var noteList: List<MathNote> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_note, parent, false)
            return NoteViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val currentNote = noteList[position]
            holder.bind(currentNote)
        }

        override fun getItemCount(): Int {
            return noteList.size
        }

        fun submitList(newList: List<MathNote>) {
            noteList = newList
            notifyDataSetChanged()
        }

        inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(note: MathNote) {
                itemView.findViewById<TextView>(R.id.tvNoteTitle).text = note.title
                itemView.findViewById<TextView>(R.id.tvNoteContent).text = note.content

                // Слухач на клік для переходу до деталей замітки
                itemView.setOnClickListener {
                    // Визначити дії, які виконуються при натисканні на елемент
                    val context = itemView.context
                    val viewNoteFragment = ViewNoteFragment()

                    val bundle = Bundle().apply {
                        putString("note_title", note.title)
                        putString("note_content", note.content)
                    }
                    viewNoteFragment.arguments = bundle

                    val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.createViewNoteFragment, viewNoteFragment)
                    transaction.addToBackStack(null)

                    transaction.commit()
                }
            }
        }
    }

    private fun openViewNoteFragment(note: MathNote) {
        val viewNoteFragment = ViewNoteFragment()
        val bundle = Bundle().apply {
            putString("note_title", note.title)
            putString("note_content", note.content)
        }
        viewNoteFragment.arguments = bundle

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.createViewNoteFragment, viewNoteFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun getNotes(): List<MathNote> {
        return listOf(
            MathNote("Algebra Basics", "Review basic algebraic concepts and operations."),
            MathNote("Geometry Formulas", "Learn and memorize important geometry formulas."),
            MathNote("Calculus Fundamentals", "Understand the fundamentals of calculus, including limits and derivatives."),
            MathNote("Trigonometry Identities", "Memorize common trigonometric identities for solving problems."),
            MathNote("Linear Equations", "Solve linear equations and systems of linear equations."),
            MathNote("Quadratic Equations", "Learn methods for solving quadratic equations."),
            MathNote("Probability Theory", "Explore the basics of probability theory and its applications."),
            MathNote("Statistics Concepts", "Understand key statistical concepts and measures."),
            MathNote("Integration Techniques", "Study various techniques of integration in calculus."),
            MathNote("Differential Equations", "Introduction to solving ordinary differential equations."),
            MathNote("Vectors and Matrices", "Understand vector and matrix operations."),
            MathNote("Complex Numbers", "Learn about operations with complex numbers."),
            MathNote("Number Theory", "Explore properties and relationships of numbers."),
            MathNote("Set Theory", "Introduction to the basic concepts of set theory."),
            MathNote("Logic and Proof Techniques", "Study logical reasoning and proof techniques."),
            MathNote("Advanced Topics in Calculus", "Explore advanced topics in calculus, such as multivariable calculus.")
        )
    }

//    inner class MathNoteAdapter(val noteList: List<MathNote>) :
//        RecyclerView.Adapter<MathNoteAdapter.ViewHolder>() {
//        inner class ViewHolder(view: View) : RecyclerView.ViewHolder
//            (view) {
//            val newsTitle: TextView = view.findViewById(R.id.twNoteTitle)
//        }
//
//        override fun onCreateViewHolder(
//            parent: ViewGroup, viewType:
//            Int
//        ): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.news_item, parent, false)
//            val holder = ViewHolder(view)
//            holder.itemView.setOnClickListener {
//                val note = noteList[holder.adapterPosition]
////                if (isTwoPane) {
////                    val fragment =
////                        activity?.supportFragmentManager?.findFragmentById(R.id.newsContentFrag) as NewsContentFragment
////                    fragment.refresh(news.title, news.content)
////                } else {
//                MainActivity.actionStart(
//                    parent.context, note.title,
//                    note.content
//                )
////                }
//            }
//            return holder
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val note = noteList[position]
//            holder.twNoteTitle.text = note.title
//        }
//        override fun getItemCount() = noteList.size
//    }
}
