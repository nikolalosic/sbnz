package sbnz.soft.nikola.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import sbnz.soft.nikola.domain.enumeration.MedicineType;

/**
 * A Medicine.
 */
@Entity
@Table(name = "medicine")
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private MedicineType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "medicine_ingredients",
               joinColumns = @JoinColumn(name = "medicines_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ingredients_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Medicine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicineType getType() {
        return type;
    }

    public Medicine type(MedicineType type) {
        this.type = type;
        return this;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Medicine ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Medicine addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public Medicine removeIngredients(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicine medicine = (Medicine) o;
        if (medicine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medicine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
