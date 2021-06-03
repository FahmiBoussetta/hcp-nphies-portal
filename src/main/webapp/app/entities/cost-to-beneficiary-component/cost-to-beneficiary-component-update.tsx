import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cost-to-beneficiary-component.reducer';
import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICostToBeneficiaryComponentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CostToBeneficiaryComponentUpdate = (props: ICostToBeneficiaryComponentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { costToBeneficiaryComponentEntity, coverages, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/cost-to-beneficiary-component');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverages();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...costToBeneficiaryComponentEntity,
        ...values,
        coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.costToBeneficiaryComponent.home.createOrEditLabel"
            data-cy="CostToBeneficiaryComponentCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.home.createOrEditLabel">
              Create or edit a CostToBeneficiaryComponent
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : costToBeneficiaryComponentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="cost-to-beneficiary-component-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="cost-to-beneficiary-component-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="cost-to-beneficiary-component-type">
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.type">Type</Translate>
                </Label>
                <AvInput
                  id="cost-to-beneficiary-component-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && costToBeneficiaryComponentEntity.type) || 'Gpvisit'}
                >
                  <option value="Gpvisit">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Gpvisit')}</option>
                  <option value="Spvisit">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Spvisit')}</option>
                  <option value="Copaypct">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Copaypct')}</option>
                  <option value="Copay">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Copay')}</option>
                  <option value="Deductible">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Deductible')}</option>
                  <option value="Maxoutofpocket">{translate('hcpNphiesPortalApp.CostToBeneficiaryTypeEnum.Maxoutofpocket')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="isMoneyLabel">
                  <AvInput
                    id="cost-to-beneficiary-component-isMoney"
                    data-cy="isMoney"
                    type="checkbox"
                    className="form-check-input"
                    name="isMoney"
                  />
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.isMoney">Is Money</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="cost-to-beneficiary-component-value">
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.value">Value</Translate>
                </Label>
                <AvField
                  id="cost-to-beneficiary-component-value"
                  data-cy="value"
                  type="text"
                  name="value"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="cost-to-beneficiary-component-coverage">
                  <Translate contentKey="hcpNphiesPortalApp.costToBeneficiaryComponent.coverage">Coverage</Translate>
                </Label>
                <AvInput
                  id="cost-to-beneficiary-component-coverage"
                  data-cy="coverage"
                  type="select"
                  className="form-control"
                  name="coverageId"
                >
                  <option value="" key="0" />
                  {coverages
                    ? coverages.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/cost-to-beneficiary-component" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  coverages: storeState.coverage.entities,
  costToBeneficiaryComponentEntity: storeState.costToBeneficiaryComponent.entity,
  loading: storeState.costToBeneficiaryComponent.loading,
  updating: storeState.costToBeneficiaryComponent.updating,
  updateSuccess: storeState.costToBeneficiaryComponent.updateSuccess,
});

const mapDispatchToProps = {
  getCoverages,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CostToBeneficiaryComponentUpdate);
