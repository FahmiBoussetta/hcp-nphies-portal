import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { getEntities as getResponseInsuranceItems } from 'app/entities/response-insurance-item/response-insurance-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './insurance-benefit.reducer';
import { IInsuranceBenefit } from 'app/shared/model/insurance-benefit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInsuranceBenefitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InsuranceBenefitUpdate = (props: IInsuranceBenefitUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { insuranceBenefitEntity, responseInsuranceItems, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/insurance-benefit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getResponseInsuranceItems();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...insuranceBenefitEntity,
        ...values,
        responseInsuranceItem: responseInsuranceItems.find(it => it.id.toString() === values.responseInsuranceItemId.toString()),
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
          <h2 id="hcpNphiesPortalApp.insuranceBenefit.home.createOrEditLabel" data-cy="InsuranceBenefitCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.home.createOrEditLabel">Create or edit a InsuranceBenefit</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : insuranceBenefitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="insurance-benefit-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="insurance-benefit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="allowedLabel" for="insurance-benefit-allowed">
                  <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.allowed">Allowed</Translate>
                </Label>
                <AvField id="insurance-benefit-allowed" data-cy="allowed" type="text" name="allowed" />
              </AvGroup>
              <AvGroup>
                <Label id="usedLabel" for="insurance-benefit-used">
                  <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.used">Used</Translate>
                </Label>
                <AvField id="insurance-benefit-used" data-cy="used" type="text" name="used" />
              </AvGroup>
              <AvGroup>
                <Label for="insurance-benefit-responseInsuranceItem">
                  <Translate contentKey="hcpNphiesPortalApp.insuranceBenefit.responseInsuranceItem">Response Insurance Item</Translate>
                </Label>
                <AvInput
                  id="insurance-benefit-responseInsuranceItem"
                  data-cy="responseInsuranceItem"
                  type="select"
                  className="form-control"
                  name="responseInsuranceItemId"
                >
                  <option value="" key="0" />
                  {responseInsuranceItems
                    ? responseInsuranceItems.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/insurance-benefit" replace color="info">
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
  responseInsuranceItems: storeState.responseInsuranceItem.entities,
  insuranceBenefitEntity: storeState.insuranceBenefit.entity,
  loading: storeState.insuranceBenefit.loading,
  updating: storeState.insuranceBenefit.updating,
  updateSuccess: storeState.insuranceBenefit.updateSuccess,
});

const mapDispatchToProps = {
  getResponseInsuranceItems,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InsuranceBenefitUpdate);
