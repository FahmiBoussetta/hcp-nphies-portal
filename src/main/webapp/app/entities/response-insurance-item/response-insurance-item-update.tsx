import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IResponseInsurance } from 'app/shared/model/response-insurance.model';
import { getEntities as getResponseInsurances } from 'app/entities/response-insurance/response-insurance.reducer';
import { getEntity, updateEntity, createEntity, reset } from './response-insurance-item.reducer';
import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IResponseInsuranceItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ResponseInsuranceItemUpdate = (props: IResponseInsuranceItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { responseInsuranceItemEntity, responseInsurances, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/response-insurance-item');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getResponseInsurances();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...responseInsuranceItemEntity,
        ...values,
        responseInsurance: responseInsurances.find(it => it.id.toString() === values.responseInsuranceId.toString()),
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
          <h2 id="hcpNphiesPortalApp.responseInsuranceItem.home.createOrEditLabel" data-cy="ResponseInsuranceItemCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.createOrEditLabel">
              Create or edit a ResponseInsuranceItem
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : responseInsuranceItemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="response-insurance-item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="response-insurance-item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="categoryLabel" for="response-insurance-item-category">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.category">Category</Translate>
                </Label>
                <AvField id="response-insurance-item-category" data-cy="category" type="text" name="category" />
              </AvGroup>
              <AvGroup check>
                <Label id="excludedLabel">
                  <AvInput
                    id="response-insurance-item-excluded"
                    data-cy="excluded"
                    type="checkbox"
                    className="form-check-input"
                    name="excluded"
                  />
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.excluded">Excluded</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="response-insurance-item-name">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.name">Name</Translate>
                </Label>
                <AvField id="response-insurance-item-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="response-insurance-item-description">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.description">Description</Translate>
                </Label>
                <AvField id="response-insurance-item-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="networkLabel" for="response-insurance-item-network">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.network">Network</Translate>
                </Label>
                <AvField id="response-insurance-item-network" data-cy="network" type="text" name="network" />
              </AvGroup>
              <AvGroup>
                <Label id="unitLabel" for="response-insurance-item-unit">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.unit">Unit</Translate>
                </Label>
                <AvField id="response-insurance-item-unit" data-cy="unit" type="text" name="unit" />
              </AvGroup>
              <AvGroup>
                <Label id="termLabel" for="response-insurance-item-term">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.term">Term</Translate>
                </Label>
                <AvField id="response-insurance-item-term" data-cy="term" type="text" name="term" />
              </AvGroup>
              <AvGroup>
                <Label for="response-insurance-item-responseInsurance">
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.responseInsurance">Response Insurance</Translate>
                </Label>
                <AvInput
                  id="response-insurance-item-responseInsurance"
                  data-cy="responseInsurance"
                  type="select"
                  className="form-control"
                  name="responseInsuranceId"
                >
                  <option value="" key="0" />
                  {responseInsurances
                    ? responseInsurances.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/response-insurance-item" replace color="info">
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
  responseInsurances: storeState.responseInsurance.entities,
  responseInsuranceItemEntity: storeState.responseInsuranceItem.entity,
  loading: storeState.responseInsuranceItem.loading,
  updating: storeState.responseInsuranceItem.updating,
  updateSuccess: storeState.responseInsuranceItem.updateSuccess,
});

const mapDispatchToProps = {
  getResponseInsurances,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ResponseInsuranceItemUpdate);
