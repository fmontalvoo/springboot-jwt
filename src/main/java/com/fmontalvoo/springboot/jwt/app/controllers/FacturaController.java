package com.fmontalvoo.springboot.jwt.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fmontalvoo.springboot.jwt.app.entities.Cliente;
import com.fmontalvoo.springboot.jwt.app.entities.Factura;
import com.fmontalvoo.springboot.jwt.app.entities.ItemFactura;
import com.fmontalvoo.springboot.jwt.app.entities.Producto;
import com.fmontalvoo.springboot.jwt.app.services.IClienteService;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/factura")
@SessionAttributes("factura")
//@PreAuthorize("hasRole('ROLE_USER')")
public class FacturaController {

	@Autowired
	private IClienteService cs;

//	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{clienteId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String crear(@PathVariable Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = cs.findById(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/list";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.addAttribute("titulo", "Crear factura -> ");
		model.addAttribute("factura", factura);

		return "factura/form";
	}

	@PostMapping("/form")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemsId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidades, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear factura -> ");
			return "factura/form";
		}

		if (itemsId == null || itemsId.length == 0) {
			model.addAttribute("titulo", "Crear factura -> ");
			model.addAttribute("error", "Error: Debe agregar detalles a la factura.");
			return "factura/form";
		}

		for (int i = 0; i < itemsId.length; i++) {
			Producto producto = cs.findProductoById(itemsId[i]);
			factura.addItemFactura(new ItemFactura(cantidades[i], producto));
		}

		cs.saveFactura(factura);
		status.setComplete();

		flash.addFlashAttribute("success", "Factura registrada con exito");

		return "redirect:/view/".concat(factura.getCliente().getId().toString());
	}

//	@Secured("ROLE_USER")
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {
//		Factura factura = cs.findFacturaById(id);
		Factura factura = cs.fetchByIdWithClienteWithItemFacturaWithProducto(id);
		if (factura == null) {
			flash.addFlashAttribute("error", "Factura no existe");
			return "redirect:/list";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getCliente().getNombreCompleto()));
		return "factura/view";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if (id != null && id > 0) {
			Factura f = cs.findFacturaById(id);
			if (f == null) {
				flash.addFlashAttribute("error", "Factura no existe");
				return "redirect:/list";
			}
			Cliente c = f.getCliente();
			cs.deleteFactura(id);
			flash.addFlashAttribute("info", "Factura eliminada");
			return "redirect:/view/".concat(c.getId().toString());
		}

		return "redirect:/list";
	}

//	@Secured("ROLE_USER")
	@GetMapping(value = "/buscar-productos/{nombre}", produces = { "application/json" })
	public @ResponseBody List<Producto> bucarProductos(@PathVariable String nombre, Model model) {
		return cs.findProductosByName(nombre);
	}

}
