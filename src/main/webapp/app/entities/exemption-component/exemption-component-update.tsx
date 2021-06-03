import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { getEntities as getCostToBeneficiaryComponents } from 'app/entities/cost-to-beneficiary-component/cost-to-beneficiary-component.reducer';
import { getEntity, updateEntity, createEntity, reset } from './exemption-component.reducer';
import { IExemptionComponent } from 'app/shared/model/exemption-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExemptionComponentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExemptionComponentUpdate = (props: IExemptionComponentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { exemptionComponentEntity, costToBeneficiaryComponents, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/exemption-component');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCostToBeneficiaryComponents();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.start = convertDateTimeToServer(values.start);
    values.end = convertDateTimeToServer(values.end);

    if (errors.length === 0) {
      const entity = {
        ...exemptionComponentEntity,
        ...values,
        costToBeneficiary: costToBeneficiaryComponents.find(it => it.id.toString() === values.costToBeneficiaryId.toString()),
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
          <h2 id="hcpNphiesPortalApp.exemptionComponent.home.createOrEditLabel" data-cy="ExemptionComponentCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.home.createOrEditLabel">
              Create or edit a ExemptionComponent
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : exemptionComponentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="exemption-component-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="exemption-component-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="typeLabel" for="exemption-component-type">
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.type">Type</Translate>
                </Label>
                <AvInput
                  id="exemption-component-type"
                  data-cy="type"
                  type="select"
                  className="form-control"
                  name="type"
                  value={(!isNew && exemptionComponentEntity.type) || 'Retired'}
                >
                  <option value="Retired">{translate('hcpNphiesPortalApp.ExemptionTypeEnum.Retired')}</option>
                  <option value="Foster">{translate('hcpNphiesPortalApp.ExemptionTypeEnum.Foster')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="startLabel" for="exemption-component-start">
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.start">Start</Translate>
                </Label>
                <AvInput
                  id="exemption-component-start"
                  data-cy="start"
                  type="datetime-local"
                  className="form-control"
                  name="start"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.exemptionComponentEntity.start)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endLabel" for="exemption-component-end">
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.end">End</Translate>
                </Label>
                <AvInput
                  id="exemption-component-end"
                  data-cy="end"
                  type="datetime-local"
                  className="form-control"
                  name="end"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.exemptionComponentEntity.end)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="exemption-component-costToBeneficiary">
                  <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.costToBeneficiary">Cost To Beneficiary</Translate>
                </Label>
                <AvInput
                  id="exemption-component-costToBeneficiary"
                  data-cy="costToBeneficiary"
                  type="select"
                  className="form-control"
                  name="costToBeneficiaryId"
                >
                  <option value="" key="0" />
                  {costToBeneficiaryComponents
                    ? costToBeneficiaryComponents.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/exemption-component" replace color="info">
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
  costToBeneficiaryComponents: storeState.costToBeneficiaryComponent.entities,
  exemptionComponentEntity: storeState.exemptionComponent.entity,
  loading: storeState.exemptionComponent.loading,
  updating: storeState.exemptionComponent.updating,
  updateSuccess: storeState.exemptionComponent.updateSuccess,
});

const mapDispatchToProps = {
  getCostToBeneficiaryComponents,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExemptionComponentUpdate);
