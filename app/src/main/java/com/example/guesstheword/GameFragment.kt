package com.example.guesstheword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {
    var _binding:FragmentGameBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater,container,false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        updateScreen()
        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.guess.text.toString().uppercase())
            updateScreen()
            if(viewModel.isWon()||viewModel.isLost()){
                val message = viewModel.wonLostMessage()
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(result=message)
                view.findNavController().navigate(action)
            }
            binding.guess.text = null
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateScreen(){
        binding.word.text = viewModel.secretWordDisplay
        binding.lives.text = "You have ${viewModel.livesLeft} lives left"
        binding.incorrectGuesses.text = "Incorrect Guesses: ${viewModel.incorrectGuesses}"
    }

}