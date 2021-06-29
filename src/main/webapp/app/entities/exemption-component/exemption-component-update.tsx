import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICostToBeneficiaryComponent } from 'app/shared/model/cost-to-beneficiary-component.model';
import { getEntities as getCostToBeneficiaryComponents } from 'app/entities/cost-to-beneficiary-component/cost-to-beneficiary-component.reducer';
import { getEntity, updateEntity, createEntity, reset } from './exemption-component.reducer';
import { IExemptionComponent } from 'app/shared/model/exemption-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ExemptionComponentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const costToBeneficiaryComponents = useAppSelector(state => state.costToBeneficiaryComponent.entities);
  const exemptionComponentEntity = useAppSelector(state => state.exemptionComponent.entity);
  const loading = useAppSelector(state => state.exemptionComponent.loading);
  const updating = useAppSelector(state => state.exemptionComponent.updating);
  const updateSuccess = useAppSelector(state => state.exemptionComponent.updateSuccess);

  const handleClose = () => {
    props.history.push('/exemption-component');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCostToBeneficiaryComponents({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.start = convertDateTimeToServer(values.start);
    values.end = convertDateTimeToServer(values.end);

    const entity = {
      ...exemptionComponentEntity,
      ...values,
      costToBeneficiary: costToBeneficiaryComponents.find(it => it.id.toString() === values.costToBeneficiaryId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          start: displayDefaultDateTime(),
          end: displayDefaultDateTime(),
        }
      : {
          ...exemptionComponentEntity,
          type: 'Retired',
          start: convertDateTimeFromServer(exemptionComponentEntity.start),
          end: convertDateTimeFromServer(exemptionComponentEntity.end),
          costToBeneficiaryId: exemptionComponentEntity?.costToBeneficiary?.id,
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
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="exemption-component-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.exemptionComponent.type')}
                id="exemption-component-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="Retired">{translate('hcpNphiesPortalApp.ExemptionTypeEnum.Retired')}</option>
                <option value="Foster">{translate('hcpNphiesPortalApp.ExemptionTypeEnum.Foster')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.exemptionComponent.start')}
                id="exemption-component-start"
                name="start"
                data-cy="start"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.exemptionComponent.end')}
                id="exemption-component-end"
                name="end"
                data-cy="end"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="exemption-component-costToBeneficiary"
                name="costToBeneficiaryId"
                data-cy="costToBeneficiary"
                label={translate('hcpNphiesPortalApp.exemptionComponent.costToBeneficiary')}
                type="select"
              >
                <option value="" key="0" />
                {costToBeneficiaryComponents
                  ? costToBeneficiaryComponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exemption-component" replace color="info">
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
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ExemptionComponentUpdate;
